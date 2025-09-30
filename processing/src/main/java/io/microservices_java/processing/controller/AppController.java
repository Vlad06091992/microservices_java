package io.microservices_java.processing.controller;

import io.microservices_java.processing.dto.AddMoneyToAccountDTO;
import io.microservices_java.processing.dto.NewAccountDTO;
import io.microservices_java.processing.dto.TransferMoneyDTO;
import io.microservices_java.processing.model.Account;
import io.microservices_java.processing.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
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

    @PutMapping("/account/addMoney/{id}")
    public Account addMoneyToAccount(
            @RequestBody AddMoneyToAccountDTO data, @PathVariable UUID id
    ) {
        return this.service.addMoneyToAccount(id,data.getUid(),data.getQuantity());
    }


    @PostMapping("/account/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferMoneyDTO accounts)
            throws InterruptedException {
        service.transferMoney(accounts);
        return ResponseEntity.noContent().build();
    }
}
