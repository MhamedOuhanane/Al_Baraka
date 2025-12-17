package com.albaraka.albaraka.model.mapper;

import com.albaraka.albaraka.model.dto.account.AccountCreateDTO;
import com.albaraka.albaraka.model.dto.account.AccountDTO;
import com.albaraka.albaraka.model.dto.user.RegisterDTO;
import com.albaraka.albaraka.model.dto.user.UserDTO;
import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    AccountDTO toDto(Account account);

    Account toEntity(AccountCreateDTO dto);
}
