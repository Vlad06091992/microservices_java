package io.microservices_java.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.UUID;

@Data

public class NewAccountDTO {
    @JsonAlias("currency")
    private String currencyCode;
    @JsonAlias("userId")
    private UUID userId;
}
