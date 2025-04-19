package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}