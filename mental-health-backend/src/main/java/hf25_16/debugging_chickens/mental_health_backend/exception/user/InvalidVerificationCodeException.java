package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class InvalidVerificationCodeException extends RuntimeException {
    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
