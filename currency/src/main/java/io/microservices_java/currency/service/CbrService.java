package io.microservices_java.currency.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.microservices_java.currency.client.HttpClientInterface;
import io.microservices_java.currency.schema.ValCurs;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toMap;

@Service
public class CbrService {
//
    private final Cache<LocalDate, Map<String, BigDecimal>> cache;
    private final HttpClientInterface client;

    public CbrService(HttpClientInterface client) {
        this.cache = CacheBuilder.newBuilder().build();
        this.client = client;
    }

    public BigDecimal requestByCurrencyCode(String code) throws ExecutionException {
        return this.cache.get(LocalDate.now(),this::getData)
                .get(code);
    }

    public Map<String, BigDecimal> getAllValutes() throws ExecutionException {
        return this.cache.get(LocalDate.now(),this::getData);
    }


    private BigDecimal parseWithLocale(String currency) {
        try {
            double v = NumberFormat.getNumberInstance(Locale.getDefault()).parse(currency).doubleValue();
            return BigDecimal.valueOf(v);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    private Map<String, BigDecimal> getData() {
        System.out.println("use request");
        var xml = client.requestByDate(LocalDate.now());
        ValCurs response = unmarshall(xml);
        return response.getValute().stream().collect(toMap(ValCurs.Valute::getCharCode, item -> parseWithLocale(item.getValue())));
    }

    private ValCurs unmarshall(String xml) {
        try (StringReader reader = new StringReader(xml)) {
            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            return (ValCurs) context.createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
