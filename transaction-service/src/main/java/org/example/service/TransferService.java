package org.example.service;

import org.example.dto.Account;

import org.example.dto.TransactionDto;
import org.example.dto.TransferRequest;
import org.example.entity.Transaction;
import org.example.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TransferService {

    private final RestTemplate restTemplate;
    private final String ACCOUNT_SERVICE_BASE_URL = "http://account-service:8081/accounts/account";
    private final KafkaProducer kafkaProducer;
    private final TransferRepository transferRepository;
    @Autowired
    public TransferService(RestTemplate restTemplate, KafkaProducer kafkaProducer, TransferRepository transferRepository) {
        this.restTemplate = restTemplate;
        this.kafkaProducer = kafkaProducer;
        this.transferRepository = transferRepository;
    }

    public void transfer(TransferRequest request) {
        Account from = restTemplate.getForObject(
                ACCOUNT_SERVICE_BASE_URL + "/" + request.getFromAccountId(), Account.class
        );
        Account to = restTemplate.getForObject(
                ACCOUNT_SERVICE_BASE_URL + "/" + request.getToAccountId(), Account.class
        );
        if(from.getBalance() < request.getAmount()) {
            throw new IllegalArgumentException("Insufficient Balance");
        }

        from.setBalance(from.getBalance() - request.getAmount());
        to.setBalance(to.getBalance() + request.getAmount());

        restTemplate.put(ACCOUNT_SERVICE_BASE_URL + "/" + request.getFromAccountId(), from);
        restTemplate.put(ACCOUNT_SERVICE_BASE_URL + "/" + request.getToAccountId(), to);

        Transaction transaction = Transaction.builder()
                .userId(request.getUserId())
                .fromAccountId(request.getFromAccountId())
                .toAccountId(request.getToAccountId())
                .amount(request.getAmount())
                .date(request.getDate()) 
                .build();
        transferRepository.save(transaction);
        kafkaProducer.sendTransferNotification(request);
        System.out.println("kafka produced " + request);
        System.out.println("transaction saved " + transaction);

    }
    
    public List<TransactionDto> getAllTransactions(String userId){
        List<Transaction> transactions = transferRepository.findTransactionsByUserId(userId);
        return transactions.stream().map(transaction -> new TransactionDto(transaction.getId(), transaction.getUserId(),
                transaction.getFromAccountId(), transaction.getToAccountId(), transaction.getAmount(), transaction.getType(),
                transaction.getDate())).toList();
    }


}
