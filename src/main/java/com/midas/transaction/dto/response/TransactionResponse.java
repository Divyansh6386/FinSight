package com.midas.transaction.dto.response;

import com.midas.transaction.entity.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long id;

    private String symbol;

    private TransactionType type;

    private Integer quantity;

    private BigDecimal price;

    private LocalDateTime createdAt;
}