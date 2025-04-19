package hf25_16.debugging_chickens.mental_health_backend.exception.admin;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}