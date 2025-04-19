package hf25_16.debugging_chickens.mental_health_backend.exception.session;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(String message) {
        super(message);
    }
}