package hf25_16.debugging_chickens.mental_health_backend.model;

import hf25_16.debugging_chickens.mental_health_backend.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id")
    private User receiver;

    private String message;

    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @PrePersist
    protected void onSend() {
        sentAt = LocalDateTime.now();
        status = NotificationStatus.PENDING;
    }
}