package io.microservices_java.currency.client;

import java.time.LocalDate;

public interface HttpClientInterface {

    public String requestByDate(LocalDate date);
}
