package org.example.controller;

import org.example.dto.TransactionDto;
import org.example.dto.TransferRequest;
import org.example.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransferService transferService;
    public TransactionController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/{userId}")
    public List<TransactionDto> getTransactionsForUser(@PathVariable("userId") String userId) {
        return transferService.getAllTransactions(userId);
    }


    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transfer(@RequestBody TransferRequest request) {
        transferService.transfer(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Transfer successful");
        return ResponseEntity.ok(response); // JSON body 포함
    }



}
