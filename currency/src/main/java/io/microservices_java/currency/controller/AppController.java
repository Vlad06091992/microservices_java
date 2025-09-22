package io.microservices_java.currency.controller;

import io.microservices_java.currency.service.CbrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class AppController {

    @Autowired
    private CbrService service;

    @GetMapping("/rate/{code}")
    public BigDecimal currencyRateByCode(@PathVariable("code") String code) throws ExecutionException {
        System.out.println(code);
        BigDecimal result = service.requestByCurrencyCode(code);
        System.out.println(result);
        return result;
    }

    @GetMapping("/rate")
    public Map<String, BigDecimal> currencyRate() throws ExecutionException {
        Map<String, BigDecimal> result = service.getAllValutes();
        System.out.println(result);
        return result;
    }
}
