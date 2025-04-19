package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(String message) {
        super(message);
    }
}