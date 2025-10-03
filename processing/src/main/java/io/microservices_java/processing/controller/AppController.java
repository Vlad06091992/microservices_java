package io.microservices_java.processing.controller;

import io.microservices_java.processing.dto.NewAccountDTO;
import io.microservices_java.processing.dto.TransferMoneyDTO;
import io.microservices_java.processing.model.Account;
import io.microservices_java.processing.service.AccountsService;
import io.microservices_java.processing.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/processing")
@RequiredArgsConstructor
public class AppController {

    private final AccountsService accountsService;
    private final ExchangeService exchangeService;

    @PostMapping("/account")
    public Account createAccount(@RequestBody NewAccountDTO account) throws ExecutionException {
        return this.accountsService.createNewAccount(account);
    }

    @PutMapping("/account/addMoney/{id}")
    public Account addMoneyToAccount(
            @RequestBody TransferMoneyDTO data, @PathVariable UUID id
    ) {
        return this.accountsService.addMoneyToAccount(id,data.getFrom(),data.getQuantity());
    }


    @PutMapping("/account/transfer/{uid}")
    public ResponseEntity<Void> transfer(@RequestBody TransferMoneyDTO accounts, @PathVariable UUID uid)
            throws InterruptedException {
        exchangeService.exchangeCurrency(uid,accounts.getFrom(),accounts.getTo(),accounts.getQuantity());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/accounts/{userId}")
    public List<Account> getAccountsByUserId(@PathVariable UUID userId)
            throws InterruptedException {
        return accountsService.getAccountsByUserId(userId);
    }
}
