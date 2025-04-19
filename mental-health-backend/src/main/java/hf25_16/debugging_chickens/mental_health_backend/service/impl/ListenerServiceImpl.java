package hf25_16.debugging_chickens.mental_health_backend.service.impl;
import hf25_16.debugging_chickens.mental_health_backend.dto.Listener.response.FullListenerDetailsDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.UserActivity.UserActivityDTO;
import hf25_16.debugging_chickens.mental_health_backend.enums.ProfileStatus;
import hf25_16.debugging_chickens.mental_health_backend.enums.Role;
import hf25_16.debugging_chickens.mental_health_backend.exception.appointment.InvalidRequestException;
import hf25_16.debugging_chickens.mental_health_backend.exception.listener.ListenerNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.exception.user.UserNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.mapper.ListenerDetailsMapper;
import hf25_16.debugging_chickens.mental_health_backend.mapper.UserActivityMapper;
import hf25_16.debugging_chickens.mental_health_backend.model.Listener;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import hf25_16.debugging_chickens.mental_health_backend.model.UserMetrics;
import hf25_16.debugging_chickens.mental_health_backend.repository.ListenerRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.UserMetricsRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.UserRepository;
import hf25_16.debugging_chickens.mental_health_backend.service.ListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListenerServiceImpl implements ListenerService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ListenerServiceImpl.class);
    private final ListenerRepository listenerRepository;
    private final UserRepository userRepository;
    private final UserMetricsRepository userMetricsRepository;

    @Autowired
    public ListenerServiceImpl(ListenerRepository listenerRepository, UserRepository userRepository, UserMetricsRepository userMetricsRepository) {
        this.listenerRepository = listenerRepository;
        this.userRepository = userRepository;
        this.userMetricsRepository = userMetricsRepository;
    }

    @Transactional(readOnly = true)
    public FullListenerDetailsDTO getListenerDetails(String type, Integer id) {
        Listener listener;
        User user;

        if ("userId".equalsIgnoreCase(type)) {
            user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
            listener = listenerRepository.findByUser(user)
                    .orElseThrow(() -> new ListenerNotFoundException("Listener not found: " + id));
        } else if ("listenerId".equalsIgnoreCase(type)) {
            listener = listenerRepository.findById(id)
                    .orElseThrow(() -> new ListenerNotFoundException("Listener not found: " + id));
            user = listener.getUser();
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        UserMetrics metrics = userMetricsRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("UserMetrics not found: " + id));

        return ListenerDetailsMapper.toFullListenerDetailsDTO(listener, metrics);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserActivityDTO> getListenersByFilters(String status, String searchTerm, Pageable pageable) {
        logger.debug("Fetching listeners with status: {}, search term: {}, pagination: {}",
                status, searchTerm, pageable);

        ProfileStatus profileStatus = null;
        if (status != null && !status.isEmpty()) {
            try {
                profileStatus = ProfileStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error("Invalid profile status: {}", status);
                throw new InvalidRequestException("Invalid profile status: " + status);
            }
        }

        Page<Listener> listeners;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            listeners = listenerRepository.findListenersWithStatus(profileStatus, pageable);
        } else {
            String normalizedSearch = searchTerm.trim();
            listeners = listenerRepository.findListenersWithFilters(profileStatus, normalizedSearch, pageable);
        }

        return listeners.map(listener -> UserActivityMapper.toUserActivityDTO(listener.getUser()));
    }

    @Override
    @Transactional
    public String suspendOrUnsuspendListener(Integer listenerId, String action) {
        Listener listener = listenerRepository.findById(listenerId)
                .orElseThrow(() -> new RuntimeException("Listener not found for ID: " + listenerId));
        User user = listener.getUser();

        if ("suspend".equalsIgnoreCase(action)) {
            user.setRole(Role.USER);
            user.setProfileStatus(ProfileStatus.SUSPENDED);
            listenerRepository.save(listener);
            userRepository.save(user);
            return "Listener suspended successfully.";
        } else if ("unsuspend".equalsIgnoreCase(action)) {
            user.setProfileStatus(ProfileStatus.ACTIVE);
            user.setRole(Role.LISTENER);
            listenerRepository.save(listener);
            userRepository.save(user);
            return "Listener unsuspended successfully.";
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    @Override
    public void incrementMessageCount(String username, Integer count) {
        listenerRepository.incrementMessageCount(username, count);
    }
}
