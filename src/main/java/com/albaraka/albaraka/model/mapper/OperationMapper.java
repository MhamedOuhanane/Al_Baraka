package com.albaraka.albaraka.model.mapper;

import com.albaraka.albaraka.model.dto.operation.OperationDTO;
import com.albaraka.albaraka.model.dto.operation.OperationFindDTO;
import com.albaraka.albaraka.model.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, DocumentMapper.class})
public interface OperationMapper {
    @Mapping(source = "accountSource.user.fullName", target = "fullName")
    OperationDTO toDto(Operation operation);

    OperationFindDTO toFindDto(Operation operation);

    Operation toEntity(OperationDTO dto);
}
