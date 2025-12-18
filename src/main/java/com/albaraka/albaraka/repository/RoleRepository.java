package com.albaraka.albaraka.repository;

import com.albaraka.albaraka.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByUuid(UUID uuid);
    Optional<Role> findByName(String name);
}
