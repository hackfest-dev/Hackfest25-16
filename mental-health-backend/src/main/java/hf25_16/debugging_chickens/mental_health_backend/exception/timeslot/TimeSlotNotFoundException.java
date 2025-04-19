package hf25_16.debugging_chickens.mental_health_backend.exception.timeslot;

public class TimeSlotNotFoundException extends RuntimeException {
    public TimeSlotNotFoundException(String message) {
        super(message);
    }
}