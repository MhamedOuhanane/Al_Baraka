package com.albaraka.albaraka.model.dto.operation;

import com.albaraka.albaraka.model.dto.account.AccountDTO;
import com.albaraka.albaraka.model.dto.document.DocumentDTO;
import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.enums.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class OperationCreateDTO {

    @NotNull(message = "Le type d'opération est obligatoire")
    private OperationType type;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private BigDecimal amount;

    private UUID accountSourceUuid;
    private UUID accountDestinationUuid;
    private UUID documentUuid;
}
