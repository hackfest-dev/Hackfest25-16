package hf25_16.debugging_chickens.mental_health_backend.exception.token;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}