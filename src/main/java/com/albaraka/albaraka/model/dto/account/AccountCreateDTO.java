package com.albaraka.albaraka.model.dto.account;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountCreateDTO {
    private BigDecimal balance;
    private UUID userUuid;
}
