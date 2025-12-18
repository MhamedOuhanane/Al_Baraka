package com.albaraka.albaraka.repository;

import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByUuid(UUID uuid);
    List<Account> findByUser(User user);
}
