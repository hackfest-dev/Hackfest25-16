// ApplicationAlreadySubmittedException.java
package hf25_16.debugging_chickens.mental_health_backend.exception.listener;

public class ApplicationAlreadySubmittedException extends RuntimeException {
    public ApplicationAlreadySubmittedException(String message) {
        super(message);
    }
}