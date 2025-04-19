package hf25_16.debugging_chickens.mental_health_backend.service;


import hf25_16.debugging_chickens.mental_health_backend.dto.Listener.response.FullListenerDetailsDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.UserActivity.UserActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListenerService {
    FullListenerDetailsDTO getListenerDetails(String type, Integer id);
    Page<UserActivityDTO> getListenersByFilters(String status, String searchTerm, Pageable pageable);
    String suspendOrUnsuspendListener(Integer listenerId, String action);
    void incrementMessageCount(String username, Integer count);
}