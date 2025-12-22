package com.albaraka.albaraka.repository;

import com.albaraka.albaraka.model.entity.User;
import com.albaraka.albaraka.model.entity.UserOAuth;
import com.albaraka.albaraka.model.enums.OauthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserOAuthRepository extends JpaRepository<UserOAuth, UUID> {
    Optional<UserOAuth> findByUserAndProvider(User user, OauthProvider provider);
    List<UserOAuth> findByUser(User user);
}
