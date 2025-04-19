package hf25_16.debugging_chickens.mental_health_backend.exception.session;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}