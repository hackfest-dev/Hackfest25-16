package hf25_16.debugging_chickens.mental_health_backend.exception.sse;

public class UserNotOnlineException extends RuntimeException {
    public UserNotOnlineException(String message) {
        super(message);
    }
}
