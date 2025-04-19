package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}