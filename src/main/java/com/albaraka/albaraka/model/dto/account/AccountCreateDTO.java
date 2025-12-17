package com.albaraka.albaraka.model.dto.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "Le solde initial est obligatoire")
    @Positive(message = "Le solde doit être positif")
    private BigDecimal balance;

    @NotNull(message = "L'utilisateur est obligatoire")
    private UUID userUuid;
}
