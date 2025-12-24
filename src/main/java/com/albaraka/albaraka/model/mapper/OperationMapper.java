package com.albaraka.albaraka.model.mapper;

import com.albaraka.albaraka.model.dto.operation.OperationCreateDTO;
import com.albaraka.albaraka.model.dto.operation.OperationDTO;
import com.albaraka.albaraka.model.dto.operation.OperationFindDTO;
import com.albaraka.albaraka.model.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, DocumentMapper.class})
public interface OperationMapper {
    @Mapping(source = "accountSource.user.fullName", target = "fullName")
    OperationDTO toDto(Operation operation);

    OperationFindDTO toFindDto(Operation operation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "validatedAt", ignore = true)
    @Mapping(target = "executedAt", ignore = true)
    @Mapping(target = "accountSource", ignore = true)
    @Mapping(target = "accountDestination", ignore = true)
    @Mapping(target = "document", ignore = true)
    Operation toEntity(OperationCreateDTO dto);


    List<OperationDTO> toDtos(List<Operation> operations);
}
