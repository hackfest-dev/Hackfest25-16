package hf25_16.debugging_chickens.mental_health_backend.exception.appointment;

public class InvalidAppointmentStatusException extends RuntimeException {
    public InvalidAppointmentStatusException(String message) {
        super(message);
    }
}