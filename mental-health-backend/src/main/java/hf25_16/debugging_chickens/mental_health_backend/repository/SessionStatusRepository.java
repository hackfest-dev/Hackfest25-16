package hf25_16.debugging_chickens.mental_health_backend.repository;

import hf25_16.debugging_chickens.mental_health_backend.enums.SessionCategory;
import hf25_16.debugging_chickens.mental_health_backend.model.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionStatusRepository extends JpaRepository<SessionStatus, Integer> {
    List<SessionStatus> findByIsSessionStatusComputedFalse();
    List<SessionStatus> findByCategory(SessionCategory category);
}