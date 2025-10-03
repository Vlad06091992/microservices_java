package io.microservices_java.processing.service;

import io.microservices_java.processing.dto.NewAccountDTO;
import io.microservices_java.processing.model.Account;
import io.microservices_java.processing.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BigDecimal transferMoney(UUID uid, UUID fromId, UUID toId, BigDecimal money) throws InterruptedException {
//        boolean active = TransactionSynchronizationManager.isActualTransactionActive();
//        System.out.println("Транзакция активна? " + active);




        Account from = accountsRepository.findByIdForUpdate(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Счет отправителя не найден"));

        Account to = accountsRepository.findByIdForUpdate(toId)
                .orElseThrow(() -> new IllegalArgumentException("Счет получателя не найден"));

        if (from.getBalance().compareTo(money) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }

        // для теста пессимистичной блокировки
//        Thread.sleep(10000);

        from.setBalance(from.getBalance().subtract(money));
        to.setBalance(to.getBalance().add(money));
        accountsRepository.save(from);
        accountsRepository.save(to);

        return money;
    }

    @Transactional
    public Account addMoneyToAccount(UUID id, UUID accountId, BigDecimal money) {
        Account account = this.getAccountById(accountId);

        account.setBalance(account.getBalance().add(money));
        accountsRepository.save(account);

        return account;
    }

    @Transactional
    public Account getAccountById(UUID id) {
        return accountsRepository.findByIdForUpdate(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

}