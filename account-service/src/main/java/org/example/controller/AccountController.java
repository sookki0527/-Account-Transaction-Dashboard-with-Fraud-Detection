package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountDto;
import org.example.dto.UpdateBalanceRequest;
import org.example.entity.Account;
import org.example.repository.AccountRepository;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private AccountService accountService;
    private AccountRepository accountRepository;

    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/{userId}")
    public List<AccountDto> getAccountsForUser(@PathVariable("userId") String userId) {
        return accountService.getAccountsByUserId(userId);
    }

    @GetMapping("/account/{accountId}")
    public AccountDto getAccountById(@PathVariable("accountId") Long accountId){
        return accountService.getAccountById(accountId);
    }

    @PutMapping("/account/{accountId}")
    public ResponseEntity<Void> updateAccount(
            @PathVariable("accountId") Long accountId,
            @RequestBody UpdateBalanceRequest request
    ) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(request.getBalance());
        accountRepository.save(account);
        return ResponseEntity.ok().build();

    }

}
