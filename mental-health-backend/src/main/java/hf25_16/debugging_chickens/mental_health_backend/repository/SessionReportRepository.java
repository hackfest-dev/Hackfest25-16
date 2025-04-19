package hf25_16.debugging_chickens.mental_health_backend.repository;

import hf25_16.debugging_chickens.mental_health_backend.model.SessionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionReportRepository extends JpaRepository<SessionReport, Integer> {
    List<SessionReport> findBySession_SessionId(Integer sessionId);
    List<SessionReport> findByUser_UserId(Integer userId);
}