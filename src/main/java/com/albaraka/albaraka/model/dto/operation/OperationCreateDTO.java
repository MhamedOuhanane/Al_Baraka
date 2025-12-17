package com.albaraka.albaraka.model.dto.operation;

import com.albaraka.albaraka.model.dto.account.AccountDTO;
import com.albaraka.albaraka.model.dto.document.DocumentDTO;
import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.enums.OperationType;
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
    private OperationType type;
    private BigDecimal amount;
    private UUID accountSourceUuid;
    private UUID accountDestinationUuid;
    private UUID documentUuid;
}
