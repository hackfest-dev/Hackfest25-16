// ListenerApplicationSummaryResponseDTO.java
package hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.response;

import hf25_16.debugging_chickens.mental_health_backend.enums.ListenerApplicationStatus;
import lombok.Data;

@Data
public class ListenerApplicationSummaryResponseDTO {
    private Integer applicationId;
    private String fullName;
    private String branch;
    private Integer semester;
    private ListenerApplicationStatus applicationStatus;
    private String certificateUrl;
}