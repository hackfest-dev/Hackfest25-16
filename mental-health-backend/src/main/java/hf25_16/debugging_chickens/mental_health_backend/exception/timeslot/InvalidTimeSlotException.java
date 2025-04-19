// InvalidTimeSlotException.java
package hf25_16.debugging_chickens.mental_health_backend.exception.timeslot;

public class InvalidTimeSlotException extends RuntimeException {
    public InvalidTimeSlotException(String message) {
        super(message);
    }
}