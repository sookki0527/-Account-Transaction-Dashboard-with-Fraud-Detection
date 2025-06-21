package org.example.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.dto.NotificationResponse;
import org.example.dto.TransferEvent;
import org.example.dto.TransferRequest;
import org.example.dto.UpdateBalanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class KafkaConsumer {
    @Autowired
    private SimpMessagingTemplate  messagingTemplate;

    public KafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "transfer-topic", groupId = "notif-group")
    public void listen(TransferRequest event){
        NotificationResponse notif = new NotificationResponse(
                "transfer",
                "💸 $" + event.getAmount() + " transferred.",
                "Just Now"
        );
        System.out.println("✅ Consumed Kafka message: " + notif);

        messagingTemplate.convertAndSend("/topic/notifications", notif);
    }

    @KafkaListener(topics = "account-topic", groupId = "notif-group")
    public void listen(UpdateBalanceRequest event){
        NotificationResponse notif = new NotificationResponse(
                "account",
                "💸 $" + event.getBalance()+ " is " + event.getType() + " from account" + event.getAccountId(),
                "Just Now"
        );
        System.out.println("✅ Consumed Kafka message: " + notif);

        messagingTemplate.convertAndSend("/topic/notifications", notif);
    }

    @KafkaListener(topics = "ai-topic", groupId = "notif-group")
    public void listen(String message){
        NotificationResponse notif = new NotificationResponse(
                "ai",
                message,
                "Just Now"
        );
        System.out.println("✅ Consumed Kafka message: " + notif);

        messagingTemplate.convertAndSend("/topic/notifications-ai", notif);
    }
}
