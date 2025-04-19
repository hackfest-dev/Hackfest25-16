package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}