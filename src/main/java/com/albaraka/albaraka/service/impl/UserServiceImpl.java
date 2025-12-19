package com.albaraka.albaraka.service.impl;

import com.albaraka.albaraka.exception.generic.ConflictException;
import com.albaraka.albaraka.exception.generic.InvalidRequestException;
import com.albaraka.albaraka.exception.generic.ResourceNotFoundException;
import com.albaraka.albaraka.model.dto.user.RegisterDTO;
import com.albaraka.albaraka.model.dto.user.UserDTO;
import com.albaraka.albaraka.model.entity.Role;
import com.albaraka.albaraka.model.entity.User;
import com.albaraka.albaraka.model.mapper.UserMapper;
import com.albaraka.albaraka.repository.RoleRepository;
import com.albaraka.albaraka.repository.UserRepository;
import com.albaraka.albaraka.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword()))
            throw new InvalidRequestException("Les mots de passe ne correspondent pas !");

        if (repository.existsByEmail(dto.getEmail()))
            throw new ConflictException("Email déjà utilisé !");

        Role role = roleRepository.findByName("ROLE_CLIENT")
                .orElseThrow(() -> new ResourceNotFoundException("Aucun role exist avec le nom " + dto.getRole()));

        User user = mapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        user = repository.save(user);

        return mapper.toDto(user);
    }
}
