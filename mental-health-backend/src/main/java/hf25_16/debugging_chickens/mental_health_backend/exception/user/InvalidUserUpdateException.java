package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class InvalidUserUpdateException extends RuntimeException {
    public InvalidUserUpdateException(String message) {
        super(message);
    }
}