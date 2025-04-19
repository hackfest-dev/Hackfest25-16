package hf25_16.debugging_chickens.mental_health_backend.exception.emergency;

public class InvalidEmergencyHelplineException extends RuntimeException {
    public InvalidEmergencyHelplineException(String message) {
        super(message);
    }
}