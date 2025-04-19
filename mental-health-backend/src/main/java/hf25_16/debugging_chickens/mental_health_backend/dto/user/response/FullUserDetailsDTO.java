package hf25_16.debugging_chickens.mental_health_backend.dto.user.response;

import hf25_16.debugging_chickens.mental_health_backend.enums.ProfileStatus;
import hf25_16.debugging_chickens.mental_health_backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FullUserDetailsDTO {
    private Integer userId;
    private String email;
    private String anonymousName;
    private Role role;
    private Boolean isActive;
    private ProfileStatus profileStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastSeen;
    private int totalSessionsAttended;
    private LocalDateTime lastSessionDate;
    private int totalAppointments;
    private LocalDateTime lastAppointmentDate;
    private int totalMessagesSent;
    private int totalBlogsPublished;
    private int totalLikesReceived;
    private int totalViewsReceived;
}