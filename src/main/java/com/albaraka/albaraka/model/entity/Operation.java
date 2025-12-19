package com.albaraka.albaraka.model.entity;

import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "operations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "uuid", nullable = false, unique = true, updatable = false)
    @EqualsAndHashCode.Include
    private UUID uuid;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type = OperationType.DEPOSIT;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationStatus status = OperationStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "validated_at", nullable = false)
    private LocalDateTime validatedAt;

    @Column(name = "executed_at", nullable = false)
    private LocalDateTime executedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_source_id")
    private Account accountSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_destination_id")
    private Account accountDestination;

    @OneToOne(mappedBy = "operation", fetch = FetchType.LAZY)
    private Document document;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
