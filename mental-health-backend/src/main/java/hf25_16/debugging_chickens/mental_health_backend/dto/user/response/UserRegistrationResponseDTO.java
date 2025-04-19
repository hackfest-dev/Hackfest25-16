package hf25_16.debugging_chickens.mental_health_backend.dto.user.response;
import hf25_16.debugging_chickens.mental_health_backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationResponseDTO {
    private Integer userId;
    private String email;
    private String anonymousName;
    private Role role;
}