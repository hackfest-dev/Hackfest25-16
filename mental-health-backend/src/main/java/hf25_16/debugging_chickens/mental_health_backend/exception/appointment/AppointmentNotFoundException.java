package hf25_16.debugging_chickens.mental_health_backend.exception.appointment;
public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
