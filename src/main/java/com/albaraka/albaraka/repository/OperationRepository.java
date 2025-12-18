package com.albaraka.albaraka.repository;

import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.Operation;
import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.enums.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
    Optional<Operation> findByUuid(UUID uuid);
    List<Operation> findByStatusAndByType(OperationStatus status, OperationType type);
    List<Operation> findByAccount(Account account);
}
