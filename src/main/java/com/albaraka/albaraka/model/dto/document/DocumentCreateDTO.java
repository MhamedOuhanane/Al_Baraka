package com.albaraka.albaraka.model.dto.document;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentCreateDTO {

    @NotNull(message = "L'Operation est obligatoire")
    private UUID operationUuid;
}
