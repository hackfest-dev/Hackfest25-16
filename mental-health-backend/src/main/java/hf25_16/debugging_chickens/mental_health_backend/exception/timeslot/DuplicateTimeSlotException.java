// DuplicateTimeSlotException.java
package hf25_16.debugging_chickens.mental_health_backend.exception.timeslot;

public class DuplicateTimeSlotException extends RuntimeException {
    public DuplicateTimeSlotException(String message) {
        super(message);
    }
}