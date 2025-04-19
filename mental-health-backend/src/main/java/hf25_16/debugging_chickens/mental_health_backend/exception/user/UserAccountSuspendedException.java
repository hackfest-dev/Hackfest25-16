
package hf25_16.debugging_chickens.mental_health_backend.exception.user;

public class UserAccountSuspendedException extends RuntimeException {
    public UserAccountSuspendedException(String message) {
        super(message);
    }
}