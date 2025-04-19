package hf25_16.debugging_chickens.mental_health_backend.repository;

import hf25_16.debugging_chickens.mental_health_backend.model.ChatMessage;
import hf25_16.debugging_chickens.mental_health_backend.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findBySession_SessionId(Integer sessionId);

}
