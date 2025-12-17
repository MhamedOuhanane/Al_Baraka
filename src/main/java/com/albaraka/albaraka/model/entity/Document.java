package com.albaraka.albaraka.model.entity;

import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Document {
    private Long id;
    private UUID uuid;
    private String fileName;
    private String fileType;
    private String storagePath;
    private LocalDateTime uploadedAt;
    private Operation operation;
}
