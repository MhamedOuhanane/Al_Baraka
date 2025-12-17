package com.albaraka.albaraka.model.mapper;

import com.albaraka.albaraka.model.dto.user.RegisterDTO;
import com.albaraka.albaraka.model.dto.user.UserDTO;
import com.albaraka.albaraka.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.name", target = "role")
    UserDTO toDto(User user);

    User toEntity(RegisterDTO dto);
}
