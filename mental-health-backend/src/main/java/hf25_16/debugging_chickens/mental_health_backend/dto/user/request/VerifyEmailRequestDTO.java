package hf25_16.debugging_chickens.mental_health_backend.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailRequestDTO {
    private String email;
}