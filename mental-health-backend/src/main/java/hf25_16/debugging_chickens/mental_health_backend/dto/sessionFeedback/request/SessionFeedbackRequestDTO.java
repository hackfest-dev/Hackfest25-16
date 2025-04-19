package hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.request;

import lombok.Data;

@Data
public class SessionFeedbackRequestDTO {
    private Integer sessionId;
    private Integer rating;
    private String comments;
}