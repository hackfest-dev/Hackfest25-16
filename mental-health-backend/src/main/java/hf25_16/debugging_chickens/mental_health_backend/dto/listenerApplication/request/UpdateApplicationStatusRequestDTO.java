package hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.request;


import lombok.Data;

@Data
public class UpdateApplicationStatusRequestDTO {
    private String status;
    private String rejectionReason;
}