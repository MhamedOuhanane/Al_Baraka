package com.albaraka.albaraka.repository;

import com.albaraka.albaraka.model.entity.Role;
import com.albaraka.albaraka.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUuid(UUID uuid);

    @Query("""
            SELECT u FROM User u
            JOIN FETCH u.role
            WHERE u.email = :email
            """)
    Optional<User> findByEmail(@Param("email") String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
}
