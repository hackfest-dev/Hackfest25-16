package hf25_16.debugging_chickens.mental_health_backend.repository;


import hf25_16.debugging_chickens.mental_health_backend.model.Listener;
import hf25_16.debugging_chickens.mental_health_backend.model.SessionFeedback;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionFeedbackRepository extends JpaRepository<SessionFeedback, Integer> {
    List<SessionFeedback> findBySession_SessionId(Integer sessionId);
    List<SessionFeedback> findByListener_ListenerId(Integer listenerId);
    List<SessionFeedback> findByUser_UserId(Integer userId);
    List<SessionFeedback> findByUser(User user);
    List<SessionFeedback> findByListener(Listener listener);
}
