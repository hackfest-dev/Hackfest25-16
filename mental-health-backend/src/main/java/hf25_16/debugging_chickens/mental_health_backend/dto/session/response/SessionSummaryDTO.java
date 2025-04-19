package hf25_16.debugging_chickens.mental_health_backend.dto.session.response;

import lombok.Data;

@Data
public class SessionSummaryDTO {
    private Integer sessionId;
    private Integer userId;
    private Integer listenerId;
    private String sessionStatus;
}