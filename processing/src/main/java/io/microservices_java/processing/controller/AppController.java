package io.microservices_java.processing.controller;

import io.microservices_java.processing.dto.NewAccountDTO;
import io.microservices_java.processing.dto.TransferMoneyDTO;
import io.microservices_java.processing.model.Account;
import io.microservices_java.processing.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/processing")
@RequiredArgsConstructor
public class AppController {

    private final AccountsService service;

    @PostMapping("/account")
    public Account createAccount(@RequestBody NewAccountDTO account) throws ExecutionException {
        return this.service.createNewAccount(account);
    }


    @PostMapping("/account/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferMoneyDTO accounts)
            throws InterruptedException {
        service.transferMoney(accounts);
        return ResponseEntity.noContent().build();
    }
}
