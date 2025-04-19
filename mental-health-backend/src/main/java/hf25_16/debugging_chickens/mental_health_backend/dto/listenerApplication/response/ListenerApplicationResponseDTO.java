package hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.response;

import hf25_16.debugging_chickens.mental_health_backend.enums.ListenerApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListenerApplicationResponseDTO {
    private Integer applicationId; // Useful for frontend tracking or API interactions
    private String fullName;
    private String branch;
    private Integer semester;
    private String usn;
    private String phoneNumber;
    private String certificateUrl;
    private ListenerApplicationStatus applicationStatus;
    private String reasonForApplying;
    private LocalDateTime submissionDate;
    private String reviewedBy;
    private LocalDateTime reviewedAt;
}
