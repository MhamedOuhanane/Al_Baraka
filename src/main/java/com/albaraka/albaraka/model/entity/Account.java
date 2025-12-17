package com.albaraka.albaraka.model.entity;

import com.albaraka.albaraka.model.enums.UserStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    private Long id;
    private UUID uuid;
    private String accountNumber;
    private BigDecimal balance;
    private User user;
}
