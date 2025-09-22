package io.microservices_java.currency.client;

import io.microservices_java.currency.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Client implements HttpClientInterface {

    public static final String DATE_PATTERN = "dd/MM/yyyy";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static final String dateReq = "date_req";

    @Autowired
    public AppProperties properties;

    @Override
    public String requestByDate(LocalDate date) {
        try {
            String baseUrl = properties.getUrl();
            System.out.println("baseurl: " + baseUrl);
            HttpClient client = HttpClient.newHttpClient();

            String url = buildUrlWithQueryParams(baseUrl,dateReq, formatDateForQuery(date));
            System.out.println("url: " + url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private String buildUrlWithQueryParams(String url, String param, String value) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(param, value)
                .build().toUriString();
    }

    private String formatDateForQuery(LocalDate date) {
        return DATE_TIME_FORMATTER.format(date);
    }
}
