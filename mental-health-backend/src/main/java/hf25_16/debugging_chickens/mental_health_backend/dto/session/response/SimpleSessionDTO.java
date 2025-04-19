package hf25_16.debugging_chickens.mental_health_backend.dto.session.response;

import hf25_16.debugging_chickens.mental_health_backend.enums.SessionActivityStatus;
import hf25_16.debugging_chickens.mental_health_backend.enums.SessionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleSessionDTO {
    private Integer sessionId;
    private SessionCategory sessionCategory;
    private String sessionSummary;
}