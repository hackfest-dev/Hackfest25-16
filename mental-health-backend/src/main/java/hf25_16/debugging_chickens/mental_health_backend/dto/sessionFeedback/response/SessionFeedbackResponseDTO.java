package hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SessionFeedbackResponseDTO {
    private Integer feedbackId;
    private Integer sessionId;
    private Integer userId;
    private Integer rating;
    private String comments;
    private LocalDateTime submittedAt;
}