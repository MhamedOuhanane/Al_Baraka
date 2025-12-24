package com.albaraka.albaraka.service.impl;

import com.albaraka.albaraka.exception.generic.ResourceNotFoundException;
import com.albaraka.albaraka.model.dto.operation.OperationCreateDTO;
import com.albaraka.albaraka.model.dto.operation.OperationDTO;
import com.albaraka.albaraka.model.dto.operation.OperationFindDTO;
import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.Operation;
import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.mapper.OperationMapper;
import com.albaraka.albaraka.repository.AccountRepository;
import com.albaraka.albaraka.repository.OperationRepository;
import com.albaraka.albaraka.service.interfaces.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationRepository repository;
    private final OperationMapper mapper;
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public OperationFindDTO create(OperationCreateDTO dto) {
        Operation operation = mapper.toEntity(dto);

        Account accountSource = accountRepository.findByUuid(dto.getAccountSourceUuid())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le compte avec l'uuid '" + dto.getAccountSourceUuid() + "' n'existe pas !"
                ));

        Account accountDestination = accountRepository.findByUuid(dto.getAccountDestinationUuid())
                .orElseGet(null);

        OperationStatus status = operation.getAmount().compareTo(BigDecimal.valueOf(10_000)) <= 0
                ? OperationStatus.APPROVED
                : OperationStatus.PENDING;

        operation.setStatus(status);
        operation.setAccountSource(accountSource);
        operation.setAccountDestination(accountDestination);

        repository.save(operation);
        operation.getAccountSource();
        operation.getAccountDestination();

        return mapper.toFindDto(operation);
    }

    @Transactional
    @Override
    public OperationFindDTO updateStatus(UUID uuid, OperationStatus status) {
        Operation operation = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "L'operation avec l'uuid '" + uuid + "' n'existe pas !"
                ));

        operation.setStatus(status);

        operation.getAccountSource();
        operation.getAccountDestination();
        operation.getDocument();

        return mapper.toFindDto(operation);
    }

    @Override
    public List<OperationDTO> findAll() {
        List<Operation> operations = repository.findAll();

        return mapper.toDtos(operations);
    }
}
