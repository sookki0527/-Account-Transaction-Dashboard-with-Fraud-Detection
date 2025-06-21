package org.example.service;

import org.example.dto.DetectionDto;
import org.example.dto.NotificationResponse;
import org.example.dto.TransferRequest;
import org.example.dto.UpdateBalanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KafkaConsumer {

    private final RestTemplate restTemplate;
    private final KafkaProducer kafkaProducer;
    private String flaskUrl = "http://flask:5000/predict-advanced";

    public KafkaConsumer(RestTemplate restTemplate, KafkaProducer kafkaProducer){
        this.restTemplate = restTemplate;
        this.kafkaProducer = kafkaProducer;
    }

    @KafkaListener(topics = "transfer-topic", groupId = "notif-group")
    public void listen(TransferRequest event){
        NotificationResponse notif = new NotificationResponse(
                "💸 $" + event.getAmount() + " transferred.",
                "Just Now"
        );
        System.out.println("✅ Consumed Kafka message: " + notif);

        DetectionDto dto = DetectionDto.builder()
                .amount(event.getAmount())
                .fromAccountId(event.getFromAccountId())
                .toAccountId(event.getToAccountId())
                .userId(event.getUserId())
                .date(event.getDate())
                .type("TRANSFER")
                .build();

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, dto, Map.class);
            Integer prediction = (Integer) response.getBody().get("prediction");

            if (prediction == -1) {
                System.out.println("🚨 Anomalous transaction detected");
            } else {
                System.out.println("✅ Normal transaction");
            }
        } catch (Exception e) {
            System.out.println("Flask server call failure: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "account-topic", groupId = "notif-group")
    public void listen(UpdateBalanceRequest event){
        NotificationResponse notif = new NotificationResponse(
                "💸 $" + event.getBalance()+ " is " + event.getType(),
                "Just Now"
        );
        System.out.println("✅ Consumed Kafka message: " + notif);
        DetectionDto dto = DetectionDto.builder()
                .amount(event.getBalance())
                .fromAccountId(event.getAccountId())
                .toAccountId(event.getAccountId())
                .userId(event.getUserId())
                .date(event.getDate())
                .type(event.getType())
                .build();
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, dto, Map.class);
            Integer prediction = (Integer) response.getBody().get("prediction");

            if (prediction == -1) {
                System.out.println("🚨 Anomalous transaction detected");
                kafkaProducer.sendAINotification("🚨 Anomalous transaction detected");
            } else {
                System.out.println("✅ Normal transaction");
                kafkaProducer.sendAINotification("✅ Normal transaction");
            }
        } catch (Exception e) {
            System.out.println("Flask server call failure: " + e.getMessage());
        }
    }

}
