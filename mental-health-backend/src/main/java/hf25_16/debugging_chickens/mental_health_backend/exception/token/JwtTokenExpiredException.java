// File: backend/src/main/java/com/dbms/mentalhealth/exception/JwtTokenExpiredException.java
package hf25_16.debugging_chickens.mental_health_backend.exception.token;

public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}