package hf25_16.debugging_chickens.mental_health_backend.service;

import hf25_16.debugging_chickens.mental_health_backend.model.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    SseEmitter createEmitter(Integer userId);
    void sendNotification(Notification notification);
}