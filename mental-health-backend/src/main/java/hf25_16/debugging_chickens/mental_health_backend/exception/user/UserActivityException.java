package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class UserActivityException extends RuntimeException {
    public UserActivityException(String message) {
        super(message);
    }
}