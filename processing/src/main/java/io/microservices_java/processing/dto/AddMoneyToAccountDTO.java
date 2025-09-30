package io.microservices_java.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AddMoneyToAccountDTO {
    @JsonAlias("uid")
    private UUID uid;
    @JsonAlias("quantity")
    private BigDecimal quantity;
}
