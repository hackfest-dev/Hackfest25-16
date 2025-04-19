package hf25_16.debugging_chickens.mental_health_backend.model;

import hf25_16.debugging_chickens.mental_health_backend.enums.SessionCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "session_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer statusId;

    @OneToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private SessionCategory category;

    @Column(name = "is_session_status_computed", nullable = false)
    private Boolean isSessionStatusComputed = false;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;
}