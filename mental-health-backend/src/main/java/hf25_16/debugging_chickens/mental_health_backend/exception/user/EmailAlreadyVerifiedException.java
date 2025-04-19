package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class EmailAlreadyVerifiedException extends RuntimeException {
    public EmailAlreadyVerifiedException(String message) {
        super(message);
    }
}
