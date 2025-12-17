package com.albaraka.albaraka.model.dto.operation;

import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.enums.OperationType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OperationFinDTO {
    private UUID uuid;
    private String fullName;
    private OperationType type;
    private BigDecimal amount;
    private OperationStatus status;
}
