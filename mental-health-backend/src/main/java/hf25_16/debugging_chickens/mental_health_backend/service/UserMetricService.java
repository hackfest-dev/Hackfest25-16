package hf25_16.debugging_chickens.mental_health_backend.service;

import hf25_16.debugging_chickens.mental_health_backend.model.User;

import java.time.LocalDateTime;

public interface UserMetricService {
    void incrementMessageCount(String username, Integer count);
    void setLastSessionDate(User user, LocalDateTime lastSessionDate);
    void incrementSessionCount(User user);
    void updateAppointmentCount(User user,int count);
    void setLastAppointmentDate(User user, LocalDateTime lastAppointmentDate);
    void updateBlogCount(User user, int count);
    void updateLikeCount(User user,int count);
    void incrementViewCount(User user);

}
