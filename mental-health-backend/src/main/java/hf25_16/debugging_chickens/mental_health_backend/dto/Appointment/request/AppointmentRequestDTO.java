package hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.request;
import hf25_16.debugging_chickens.mental_health_backend.enums.SeverityLevel;
import lombok.Data;

@Data
public class AppointmentRequestDTO {
    private Integer adminId;
    private Integer timeSlotId;
    private String appointmentReason;
    private String phoneNumber;
    private String fullName;
    private SeverityLevel severityLevel;
}