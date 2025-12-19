package com.albaraka.albaraka.service.impl;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private RoleRepository roleRepository;

    @Override
    public UserDTO register(RegisterDTO dto) {
        Role role = roleRepository.findByName(dto.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun role exist avec le nom " + dto.getRole()));

        User user = mapper.toEntity(dto);
        user.setRole(role);

        user = repository.save(user);

        return mapper.toDto(user);
    }
}
