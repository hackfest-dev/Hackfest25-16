package hf25_16.debugging_chickens.mental_health_backend.exception.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String message) {
        super(message);
    }
}
