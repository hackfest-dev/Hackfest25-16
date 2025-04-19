package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
}
