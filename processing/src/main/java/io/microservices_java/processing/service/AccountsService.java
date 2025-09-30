package io.microservices_java.processing.service;

import io.microservices_java.processing.dto.NewAccountDTO;
import io.microservices_java.processing.dto.TransferMoneyDTO;
import io.microservices_java.processing.model.Account;
import io.microservices_java.processing.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsRepository accountsRepository;

    @Transactional
    public Account createNewAccount(NewAccountDTO dto) {
        //        boolean active = TransactionSynchronizationManager.isActualTransactionActive();
//        System.out.println("Транзакция активна? " + active);

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(0));
        account.setCurrencyCode(dto.getCurrencyCode());
        account.setUserId(dto.getUserId());
        return accountsRepository.save(account);
    }


    @Transactional
    public void transferMoney(TransferMoneyDTO dto) throws InterruptedException {
//        boolean active = TransactionSynchronizationManager.isActualTransactionActive();
//        System.out.println("Транзакция активна? " + active);

        BigDecimal quantity = dto.getQuantity();
        System.out.println(quantity);

        Account from = accountsRepository.findByIdForUpdate(dto.getFrom())
                .orElseThrow(() -> new IllegalArgumentException("Счет отправителя не найден"));

        Account to = accountsRepository.findByIdForUpdate(dto.getTo())
                .orElseThrow(() -> new IllegalArgumentException("Счет получателя не найден"));

        if (from.getBalance().compareTo(quantity) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }

        // для теста пессимистичной блокировки
//        Thread.sleep(10000);

        from.setBalance(from.getBalance().subtract(quantity));
        to.setBalance(to.getBalance().add(quantity));
        accountsRepository.save(from);
        accountsRepository.save(to);
    }

    @Transactional
    public Account addMoneyToAccount(UUID id, UUID accountId, BigDecimal money) {
        Account account = accountsRepository.findByIdForUpdate(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.setBalance(account.getBalance().add(money));
        accountsRepository.save(account);

        return account;
    }

}