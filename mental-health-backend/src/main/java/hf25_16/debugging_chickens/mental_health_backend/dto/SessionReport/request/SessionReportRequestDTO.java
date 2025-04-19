package hf25_16.debugging_chickens.mental_health_backend.dto.SessionReport.request;

import lombok.Data;

@Data
public class SessionReportRequestDTO {
    private Integer sessionId;
    private String reportContent;
    private Integer severityLevel;
}