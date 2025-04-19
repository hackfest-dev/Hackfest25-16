package hf25_16.debugging_chickens.mental_health_backend.dto.UserActivity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRoleCountDTO {
    private final String role;
    private final int count;
}