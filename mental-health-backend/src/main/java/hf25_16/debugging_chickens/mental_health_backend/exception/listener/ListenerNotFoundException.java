package hf25_16.debugging_chickens.mental_health_backend.exception.listener;

public class ListenerNotFoundException extends RuntimeException {
    public ListenerNotFoundException(String message) {
        super(message);
    }
}
