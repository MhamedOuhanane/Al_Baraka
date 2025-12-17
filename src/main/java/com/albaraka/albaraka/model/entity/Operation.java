package com.albaraka.albaraka.model.entity;

import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Operation {
    private Long id;
    private UUID uuid;
    private OperationType type;
    private BigDecimal amount;
    private OperationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime validatedAt;
    private LocalDateTime executedAt;
    private Account account;
    private Document document;
}
