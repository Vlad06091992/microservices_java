package io.microservices_java.processing.service;

import io.microservices_java.processing.dto.TransferMoneyDTO;
import io.microservices_java.processing.model.Account;
import io.microservices_java.processing.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final RestTemplate restClient;

    //шаблон для последующего выноса функционала перевода средств с вызовом сервиса котировок валюты

    @Value("${service.currency.url}")
    private String currencyUrl;

    private final AccountsRepository accountsRepository;


    public BigDecimal loadCurrencyRate(String currency) {
        return this.restClient.getForObject(currencyUrl + "/currency/rate/{currency}", BigDecimal.class, currency );
    }
}
