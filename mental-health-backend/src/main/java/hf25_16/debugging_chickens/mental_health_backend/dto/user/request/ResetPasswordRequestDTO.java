package hf25_16.debugging_chickens.mental_health_backend.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDTO {
    private String token;
    private String newPassword;
}