package hf25_16.debugging_chickens.mental_health_backend.mapper;

import hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.request.SessionFeedbackRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.response.SessionFeedbackResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.exception.session.SessionNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.model.Session;
import hf25_16.debugging_chickens.mental_health_backend.model.SessionFeedback;
import hf25_16.debugging_chickens.mental_health_backend.repository.SessionRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class SessionFeedbackMapper {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public SessionFeedbackMapper(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public SessionFeedback toEntity(SessionFeedbackRequestDTO dto) {
        SessionFeedback sessionFeedback = new SessionFeedback();
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new SessionNotFoundException("Session with id " + dto.getSessionId() + " not found"));
        sessionFeedback.setSession(session);
        sessionFeedback.setUser(session.getUser());
        sessionFeedback.setListener(session.getListener());
        sessionFeedback.setRating(dto.getRating());
        sessionFeedback.setComments(dto.getComments());
        return sessionFeedback;
    }

    public SessionFeedbackResponseDTO toResponseDTO(SessionFeedback entity) {
        SessionFeedbackResponseDTO dto = new SessionFeedbackResponseDTO();
        dto.setFeedbackId(entity.getFeedbackId());
        dto.setSessionId(entity.getSession().getSessionId());
        dto.setUserId(entity.getUser().getUserId());
        dto.setRating(entity.getRating());
        dto.setComments(entity.getComments());
        dto.setSubmittedAt(entity.getSubmittedAt());
        return dto;
    }
}