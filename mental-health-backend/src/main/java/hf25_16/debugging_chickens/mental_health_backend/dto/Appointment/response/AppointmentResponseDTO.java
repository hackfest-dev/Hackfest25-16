package hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.response;

import hf25_16.debugging_chickens.mental_health_backend.enums.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Integer appointmentId;
    private Integer userId; // Add this field
    private String userName;
    private String adminName;
    private Integer adminId;
    private String timeSlotDate;
    private String timeSlotStartTime;
    private String timeSlotEndTime;
    private String appointmentReason;
    private String status;
    private String phoneNumber;
    private String fullName;
    private SeverityLevel severityLevel;
}