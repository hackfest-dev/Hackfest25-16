package hf25_16.debugging_chickens.mental_health_backend.exception.listener;

public class InvalidListenerApplicationException extends RuntimeException {
    public InvalidListenerApplicationException(String message) {
        super(message);
    }
}