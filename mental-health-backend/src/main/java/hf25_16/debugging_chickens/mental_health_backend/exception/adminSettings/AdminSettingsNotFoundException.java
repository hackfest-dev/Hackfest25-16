package hf25_16.debugging_chickens.mental_health_backend.exception.adminSettings;

public class AdminSettingsNotFoundException extends RuntimeException {
    public AdminSettingsNotFoundException(String message) {
        super(message);
    }

    public AdminSettingsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}