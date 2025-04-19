package hf25_16.debugging_chickens.mental_health_backend.exception.listener;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}