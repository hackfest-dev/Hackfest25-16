package hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.request;
import lombok.Data;

@Data
public class ListenerApplicationRequestDTO {
    private String fullName;
    private String branch;
    private Integer semester;
    private String usn;
    private String reasonForApplying;
    private String phoneNumber;
}