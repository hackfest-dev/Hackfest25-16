package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}