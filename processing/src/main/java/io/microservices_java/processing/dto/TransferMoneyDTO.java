package io.microservices_java.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Data
public class TransferMoneyDTO {
    @JsonAlias("from")
    private UUID from;
    @JsonAlias("to")
    private UUID to;
    @JsonAlias("quantity")
    private BigDecimal quantity;
}
