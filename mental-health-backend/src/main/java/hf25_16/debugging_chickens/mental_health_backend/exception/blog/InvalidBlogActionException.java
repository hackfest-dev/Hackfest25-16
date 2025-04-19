package hf25_16.debugging_chickens.mental_health_backend.exception.blog;

public class InvalidBlogActionException extends RuntimeException {
    public InvalidBlogActionException(String message) {
        super(message);
    }
}