package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class AnonymousNameAlreadyInUseException extends RuntimeException {
    public AnonymousNameAlreadyInUseException(String message) {
        super(message);
    }
}