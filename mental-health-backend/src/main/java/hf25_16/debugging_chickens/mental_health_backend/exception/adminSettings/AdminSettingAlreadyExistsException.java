package hf25_16.debugging_chickens.mental_health_backend.exception.adminSettings;

public class AdminSettingAlreadyExistsException extends RuntimeException {
    public AdminSettingAlreadyExistsException(String message) {
        super(message);
    }
}
