package hf25_16.debugging_chickens.mental_health_backend.mapper;

import hf25_16.debugging_chickens.mental_health_backend.dto.SessionReport.request.SessionReportRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.SessionReport.response.SessionReportResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.exception.session.SessionNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.model.Session;
import hf25_16.debugging_chickens.mental_health_backend.model.SessionReport;
import hf25_16.debugging_chickens.mental_health_backend.repository.SessionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SessionReportMapper {

    private final SessionRepository sessionRepository;

    public SessionReportMapper(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionReport toEntity(SessionReportRequestDTO dto) {
        SessionReport sessionReport = new SessionReport();
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new SessionNotFoundException("Session with id " + dto.getSessionId() + " not found"));
        sessionReport.setSession(session);
        sessionReport.setUser(session.getUser());
        sessionReport.setListener(session.getListener());
        sessionReport.setReportContent(dto.getReportContent());
        sessionReport.setSeverityLevel(dto.getSeverityLevel());
        sessionReport.setCreatedAt(LocalDateTime.now());
        return sessionReport;
    }

    public SessionReportResponseDTO toResponseDTO(SessionReport entity) {
        SessionReportResponseDTO dto = new SessionReportResponseDTO();
        dto.setReportId(entity.getReportId());
        dto.setSessionId(entity.getSession().getSessionId());
        dto.setUserId(entity.getUser().getUserId());
        dto.setListenerId(entity.getListener().getListenerId());
        dto.setReportContent(entity.getReportContent());
        dto.setSeverityLevel(entity.getSeverityLevel());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}