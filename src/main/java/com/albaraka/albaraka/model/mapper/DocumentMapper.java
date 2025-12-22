package com.albaraka.albaraka.model.mapper;

import com.albaraka.albaraka.model.dto.document.DocumentDTO;
import com.albaraka.albaraka.model.dto.document.DocumentFindDTO;
import com.albaraka.albaraka.model.dto.operation.OperationDTO;
import com.albaraka.albaraka.model.dto.operation.OperationFindDTO;
import com.albaraka.albaraka.model.entity.Document;
import com.albaraka.albaraka.model.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDTO toDto(Document document);
    DocumentFindDTO toFindDto(Document document);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uploadedAt", ignore = true)
    @Mapping(target = "operation", ignore = true)
    Document toEntity(DocumentDTO dto);
}