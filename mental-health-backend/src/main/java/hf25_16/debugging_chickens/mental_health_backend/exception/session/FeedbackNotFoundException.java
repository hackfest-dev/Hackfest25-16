package hf25_16.debugging_chickens.mental_health_backend.exception.session;

public class FeedbackNotFoundException extends RuntimeException {
    public FeedbackNotFoundException(String message) {
        super(message);
    }
}