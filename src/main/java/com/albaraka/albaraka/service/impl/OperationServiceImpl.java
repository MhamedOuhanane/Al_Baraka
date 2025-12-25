package com.albaraka.albaraka.service.impl;

import com.albaraka.albaraka.exception.generic.InvalidRequestException;
import com.albaraka.albaraka.exception.generic.ResourceNotFoundException;
import com.albaraka.albaraka.model.dto.operation.OperationCreateDTO;
import com.albaraka.albaraka.model.dto.operation.OperationDTO;
import com.albaraka.albaraka.model.dto.operation.OperationFindDTO;
import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.Operation;
import com.albaraka.albaraka.model.entity.User;
import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.model.mapper.OperationMapper;
import com.albaraka.albaraka.repository.AccountRepository;
import com.albaraka.albaraka.repository.OperationRepository;
import com.albaraka.albaraka.repository.UserRepository;
import com.albaraka.albaraka.service.interfaces.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationRepository repository;
    private final OperationMapper mapper;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public OperationFindDTO create(OperationCreateDTO dto) {
        Operation operation = mapper.toEntity(dto);

        Account accountSource = accountRepository.findByUuid(dto.getAccountSourceUuid())
                .orElse(null);

        Account accountDestination = accountRepository.findByUuid(dto.getAccountDestinationUuid())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le compte avec l'uuid '" + dto.getAccountSourceUuid() + "' n'existe pas !"
                ));

        OperationStatus status = operation.getAmount().compareTo(BigDecimal.valueOf(10_000)) <= 0
                ? OperationStatus.APPROVED
                : OperationStatus.PENDING;


        operation.setStatus(status);
        operation.setAccountSource(accountSource);
        operation.setAccountDestination(accountDestination);

        if (status.equals(OperationStatus.APPROVED)) {
            operation.setExecutedAt(LocalDateTime.now());
            operation.setValidatedAt(LocalDateTime.now());

        }

        repository.save(operation);
        accountSource = operation.getAccountSource();
        accountDestination = operation.getAccountDestination();

        if (status.equals(OperationStatus.APPROVED)) {
            accountDestination.setBalance(accountDestination.getBalance().add(operation.getAmount()));
            accountRepository.save(accountDestination);
            if (accountSource != null) {
                if (accountSource.getBalance().compareTo(operation.getAmount()) < 0) {
                    throw new InvalidRequestException("Solde insuffisant pour confirmer cette opération.");
                }
                accountSource.setBalance(accountSource.getBalance().subtract(operation.getAmount()));
                accountRepository.save(accountSource);
            }
        }


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
        Account accountSource = operation.getAccountSource();
        Account accountDestination =operation.getAccountDestination();
        operation.getDocument();

        if (status.equals(OperationStatus.APPROVED)) {
            operation.setExecutedAt(LocalDateTime.now());
            operation.setValidatedAt(LocalDateTime.now());
            accountDestination.setBalance(accountDestination.getBalance().add(operation.getAmount()));
            accountRepository.save(accountDestination);
            if (accountSource != null) {
                if (accountSource.getBalance().compareTo(operation.getAmount()) < 0) {
                    throw new InvalidRequestException("Solde insuffisant pour confirmer cette opération.");
                }
                accountSource.setBalance(accountSource.getBalance().subtract(operation.getAmount()));
                accountRepository.save(accountSource);
            }
        }


        return mapper.toFindDto(operation);
    }

    @Override
    public List<OperationDTO> findAll() {
        List<Operation> operations = repository.findAll();

        return mapper.toDtos(operations);
    }

    @Override
    public List<OperationDTO> findAllByClient(UUID uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le client avec l'uuid '" + uuid + "' n'existe pas !"
                ));

        var operations = repository.findByUserWithDetails(user);

        return mapper.toDtos(operations);
    }
}
