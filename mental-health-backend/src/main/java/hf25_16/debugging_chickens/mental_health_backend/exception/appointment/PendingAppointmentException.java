package hf25_16.debugging_chickens.mental_health_backend.exception.appointment;

public class PendingAppointmentException extends RuntimeException {
    public PendingAppointmentException(String message) {
        super(message);
    }
}