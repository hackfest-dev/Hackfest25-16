package hf25_16.debugging_chickens.mental_health_backend.dto.TimeSlot.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimeSlotResponseDTO {
    private Integer timeSlotId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isAvailable;
}