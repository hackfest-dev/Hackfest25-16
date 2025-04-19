package hf25_16.debugging_chickens.mental_health_backend.mapper;



import hf25_16.debugging_chickens.mental_health_backend.dto.UserActivity.UserActivityDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.UserActivity.UserRoleCountDTO;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import hf25_16.debugging_chickens.mental_health_backend.service.impl.SessionServiceImpl;

import java.util.Map;

public class UserActivityMapper {

    public static UserActivityDTO toUserActivityDTO(User user) {
        boolean isInASession = SessionServiceImpl.isUserInSessionStatic(user.getUserId());
        return new UserActivityDTO(user.getUserId(), user.getAnonymousName(), isInASession);
    }

    public static UserActivityDTO toUserActivityDTO(UserActivityDTO dto) {
        boolean isInASession = SessionServiceImpl.isUserInSessionStatic(dto.getUserId());
        return new UserActivityDTO(dto.getUserId(), dto.getAnonymousName(), isInASession);
    }
    public UserRoleCountDTO toUserRoleCountDTO(Map.Entry<String, Long> entry) {
        return new UserRoleCountDTO(entry.getKey(), entry.getValue().intValue());
    }
}