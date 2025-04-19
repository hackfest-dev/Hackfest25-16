// ChatMessageService.java
package hf25_16.debugging_chickens.mental_health_backend.service;

import hf25_16.debugging_chickens.mental_health_backend.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    void saveMessages(List<ChatMessage> messages);
}