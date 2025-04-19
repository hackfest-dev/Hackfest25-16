package hf25_16.debugging_chickens.mental_health_backend.dto.TimeSlot.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
public class TimeSlotCreateRequestDTO {
    private List<TimeSlotDTO> timeSlots;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimeSlotDTO {
        private LocalTime startTime;
        private LocalTime endTime;
    }
}