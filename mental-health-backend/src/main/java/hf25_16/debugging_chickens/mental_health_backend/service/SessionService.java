package hf25_16.debugging_chickens.mental_health_backend.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import hf25_16.debugging_chickens.mental_health_backend.dto.chatMessage.ChatMessageDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.session.response.SessionResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.session.response.SessionSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SessionService {
    String initiateSession(Integer listenerId, String message) throws JsonProcessingException;
    String updateSessionStatus(Integer userId, String action);
    SessionResponseDTO getSessionById(Integer sessionId);
    String endSession(Integer sessionId);
    List<ChatMessageDTO> getMessagesBySessionId(Integer sessionId);
    String getAverageSessionDuration();
    List<SessionSummaryDTO> broadcastFullSessionCache();
    boolean isUserInSession(Integer userId);
    Page<SessionSummaryDTO> getSessionsByFilters(String status, Integer id, String idType, Pageable pageable);


}