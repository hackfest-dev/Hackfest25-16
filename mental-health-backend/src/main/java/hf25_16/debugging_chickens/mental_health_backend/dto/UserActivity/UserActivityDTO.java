package hf25_16.debugging_chickens.mental_health_backend.dto.UserActivity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserActivityDTO {
    private Integer userId;
    private String anonymousName;
    private boolean isInASession;
}