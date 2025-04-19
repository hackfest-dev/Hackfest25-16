package hf25_16.debugging_chickens.mental_health_backend.dto.adminSettings.request;

import lombok.Data;
@Data
public class AdminSettingsRequestDTO {
    private Boolean isCounsellor;
    private Integer maxAppointmentsPerDay;
    private Integer defaultTimeSlotDuration;
}