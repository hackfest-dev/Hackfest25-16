package hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.request;

import lombok.Data;

@Data
public class UpdateAppointmentStatusRequestDTO {
    private String status;
    private String cancellationReason;
}