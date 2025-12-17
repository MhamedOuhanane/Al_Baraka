package com.albaraka.albaraka.model.entity;

import com.albaraka.albaraka.model.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class User {
    private Long id;
    private UUID uuid;
    private String fullname;
    private String email;
    private String password;
    private UserStatus status;
    private LocalDateTime createdAt;
    private Role role;
    private List<Account> accounts;
}
