package com.albaraka.albaraka.repository;

import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.Operation;
import com.albaraka.albaraka.model.entity.User;
import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.enums.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
    Optional<Operation> findByUuid(UUID uuid);
    List<Operation> findByStatusAndType(OperationStatus status, OperationType type);
    List<Operation> findByAccountSource(Account account);

    @Query("""
        SELECT o FROM Operation o 
        JOIN FETCH o.accountSource acc 
        JOIN FETCH acc.user u 
        WHERE u = :user
    """)
    List<Operation> findByUserWithDetails(@Param("user") User user);
}
