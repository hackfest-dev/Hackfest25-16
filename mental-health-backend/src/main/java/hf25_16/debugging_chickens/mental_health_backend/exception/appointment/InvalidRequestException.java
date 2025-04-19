package hf25_16.debugging_chickens.mental_health_backend.exception.appointment;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}