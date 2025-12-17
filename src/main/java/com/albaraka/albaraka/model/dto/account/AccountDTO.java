package com.albaraka.albaraka.model.dto.account;

import com.albaraka.albaraka.model.entity.User;
import com.albaraka.albaraka.model.enums.UserStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountDTO {
    private UUID uuid;
    private String fullName;
    private String email;
    private String accountNumber;
    private BigDecimal balance;
}
