package com.albaraka.albaraka.repository;

import com.albaraka.albaraka.model.entity.Role;
import com.albaraka.albaraka.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUuid(UUID uuid);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
}
