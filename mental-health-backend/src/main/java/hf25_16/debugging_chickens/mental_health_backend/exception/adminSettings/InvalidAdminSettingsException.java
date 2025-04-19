package hf25_16.debugging_chickens.mental_health_backend.exception.adminSettings;

public class InvalidAdminSettingsException extends RuntimeException {
    public InvalidAdminSettingsException(String message) {
        super(message);
    }
}