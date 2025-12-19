package com.albaraka.albaraka.service.impl;

import com.albaraka.albaraka.exception.generic.ConflictException;
import com.albaraka.albaraka.exception.generic.ResourceNotFoundException;
import com.albaraka.albaraka.model.entity.User;
import com.albaraka.albaraka.repository.UserRepository;
import com.albaraka.albaraka.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun utilisateur avec email: " + username));

        return new CustomUserDetails(user);
    }
}
