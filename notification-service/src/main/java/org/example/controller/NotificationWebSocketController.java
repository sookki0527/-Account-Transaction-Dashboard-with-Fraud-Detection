package org.example.controller;

import org.example.dto.NotificationResponse;
import org.example.dto.TransferEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationWebSocketController {
    @MessageMapping("/transfer")
    @SendTo("/topic/notifications")
    public NotificationResponse formatAndSend(TransferEvent event){
        return new NotificationResponse(
                "💸 $" + event.getAmount() + " transferred.",
                "Just now"
        );
    }
}
