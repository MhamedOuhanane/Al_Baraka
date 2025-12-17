package com.albaraka.albaraka.model.dto.document;

import com.albaraka.albaraka.model.dto.operation.OperationFindDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentFindDTO {
    private UUID uuid;
    private String fileName;
    private String fileType;
    private String storagePath;
    private LocalDateTime uploadedAt;
    private OperationFindDTO operation;
}
