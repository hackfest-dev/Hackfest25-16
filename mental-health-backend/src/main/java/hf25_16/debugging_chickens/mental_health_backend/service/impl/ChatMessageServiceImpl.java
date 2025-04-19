// ChatMessageServiceImpl.java
package hf25_16.debugging_chickens.mental_health_backend.service.impl;

import hf25_16.debugging_chickens.mental_health_backend.model.ChatMessage;
import hf25_16.debugging_chickens.mental_health_backend.repository.ChatMessageRepository;
import hf25_16.debugging_chickens.mental_health_backend.service.ChatMessageService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Transactional
    public void saveMessages(List<ChatMessage> messages) {
        chatMessageRepository.saveAll(messages);
        log.info("Batch saved {} messages", messages.size());
    }


}