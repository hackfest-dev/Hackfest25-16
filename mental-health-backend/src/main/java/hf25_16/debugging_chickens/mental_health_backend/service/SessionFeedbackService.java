package hf25_16.debugging_chickens.mental_health_backend.service;


import hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.request.SessionFeedbackRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.response.SessionFeedbackResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.response.SessionFeedbackSummaryResponseDTO;

import java.util.List;

public interface SessionFeedbackService {
    SessionFeedbackResponseDTO createFeedback(SessionFeedbackRequestDTO requestDTO);
    List<SessionFeedbackResponseDTO> getFeedbackBySessionId(Integer sessionId);
    SessionFeedbackResponseDTO getFeedbackById(Integer feedbackId);
    List<SessionFeedbackResponseDTO> getAllListenerFeedback(Integer id,String type);
    SessionFeedbackSummaryResponseDTO getFeedbackSummary();
}