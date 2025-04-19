package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}