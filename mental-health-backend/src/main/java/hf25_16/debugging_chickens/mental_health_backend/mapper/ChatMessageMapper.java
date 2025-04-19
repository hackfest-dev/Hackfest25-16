package hf25_16.debugging_chickens.mental_health_backend.mapper;


import hf25_16.debugging_chickens.mental_health_backend.dto.chatMessage.ChatMessageDTO;
import hf25_16.debugging_chickens.mental_health_backend.model.ChatMessage;

public class ChatMessageMapper {
    public static ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage) {
        return ChatMessageDTO.builder()
                .messageId(chatMessage.getMessageId())
                .sessionId(chatMessage.getSession().getSessionId())
                .senderName(chatMessage.getSender().getAnonymousName())
                .senderType(chatMessage.getSender().getRole().name())
                .senderId(chatMessage.getSender().getUserId())
                .messageContent(chatMessage.getMessageContent())
                .sentAt(chatMessage.getSentAt())
                .build();
    }
}