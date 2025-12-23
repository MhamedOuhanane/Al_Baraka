package com.albaraka.albaraka.service.interfaces;

import com.albaraka.albaraka.model.dto.account.AccountCreateDTO;
import com.albaraka.albaraka.model.dto.account.AccountDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDTO insert(AccountCreateDTO dto);
    void delete(UUID uuid);
    AccountDTO updateBalance(UUID uuid, BigDecimal balance);
    List<AccountDTO> findAll();
}
