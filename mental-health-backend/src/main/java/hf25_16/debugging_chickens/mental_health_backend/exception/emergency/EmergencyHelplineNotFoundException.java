package hf25_16.debugging_chickens.mental_health_backend.exception.emergency;

public class EmergencyHelplineNotFoundException extends RuntimeException {
    public EmergencyHelplineNotFoundException(String message) {
        super(message);
    }
}