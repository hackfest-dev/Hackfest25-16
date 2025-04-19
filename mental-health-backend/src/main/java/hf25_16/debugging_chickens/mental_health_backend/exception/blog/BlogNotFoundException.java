package hf25_16.debugging_chickens.mental_health_backend.exception.blog;

public class BlogNotFoundException extends RuntimeException {
    public BlogNotFoundException(String message) {
        super(message);
    }
}