package com.albaraka.albaraka.service.impl;

import com.albaraka.albaraka.exception.generic.ResourceNotFoundException;
import com.albaraka.albaraka.model.dto.account.AccountCreateDTO;
import com.albaraka.albaraka.model.dto.account.AccountDTO;
import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.User;
import com.albaraka.albaraka.model.mapper.AccountMapper;
import com.albaraka.albaraka.repository.AccountRepository;
import com.albaraka.albaraka.repository.UserRepository;
import com.albaraka.albaraka.service.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final UserRepository userRepository;

    @Override
    public AccountDTO insert(AccountCreateDTO dto) {
        User user = userRepository.findByUuid(dto.getUserUuid()).orElseThrow(() ->
                new ResourceNotFoundException("Utilisateur pas exist avec ce uuid [" + dto.getUserUuid() + "]")
        );

        Account account = mapper.toEntity(dto);
        String accountNumber = user.getFullName().substring(0,3).toUpperCase()
                + ThreadLocalRandom.current().nextLong(1_000_000_000L, 9_999_999_999L);

        account.setAccountNumber(accountNumber);

        account = repository.save(account);

        return mapper.toDto(account);
    }

    @Override
    public void delete(UUID uuid) {
        Account account = repository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Aucun compte exist avec ce uuid [" + uuid + "]")
        );

        repository.delete(account);
    }

    @Override
    public AccountDTO updateBalance(UUID uuid, BigDecimal balance) {
        Account account = repository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Aucun compte exist avec ce uuid [" + uuid + "]")
        );

        account.setBalance(account.getBalance().add(balance));

        repository.save(account);

        return mapper.toDto(account);
    }

    @Override
    public List<AccountDTO> findAll() {
        List<Account> accounts = repository.findAll();

        return mapper.toDtos(accounts);
    }
}
