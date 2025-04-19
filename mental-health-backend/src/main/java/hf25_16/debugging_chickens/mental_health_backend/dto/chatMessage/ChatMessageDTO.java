package hf25_16.debugging_chickens.mental_health_backend.dto.chatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private Integer messageId;
    private Integer sessionId;
    private Integer senderId;
    private String senderName;
    private String senderType;
    private String messageContent;
    private LocalDateTime sentAt;
}