package hf25_16.debugging_chickens.mental_health_backend.exception.adminSettings;

public class AdminSettingNotFoundException extends RuntimeException {
    public AdminSettingNotFoundException(String message) {
        super(message);
    }
}