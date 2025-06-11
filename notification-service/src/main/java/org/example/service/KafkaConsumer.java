package org.example.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.dto.NotificationResponse;
import org.example.dto.TransferEvent;
import org.example.dto.TransferRequest;
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
                "💸 $" + event.getAmount() + " transferred.",
                "Just Now"
        );
        System.out.println("✅ Consumed Kafka message: " + notif);

        messagingTemplate.convertAndSend("/topic/notifications", notif);
    }
}
