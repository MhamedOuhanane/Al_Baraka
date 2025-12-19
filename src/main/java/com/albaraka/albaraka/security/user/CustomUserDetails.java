package com.albaraka.albaraka.security.user;

import com.albaraka.albaraka.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private UUID uuid;
    public CustomUserDetails(User user) {
        super(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getName()))
        );
        uuid = user.getUuid();
    }
}
