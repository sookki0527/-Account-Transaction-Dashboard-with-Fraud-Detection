package org.example.repository;

import org.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByUserId(String userId);
}
