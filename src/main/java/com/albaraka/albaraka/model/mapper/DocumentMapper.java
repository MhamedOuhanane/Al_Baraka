package com.albaraka.albaraka.model.mapper;

import com.albaraka.albaraka.model.dto.document.DocumentDTO;
import com.albaraka.albaraka.model.dto.document.DocumentDTO;
import com.albaraka.albaraka.model.dto.document.DocumentFindDTO;
import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDTO toDto(Document document);
    DocumentFindDTO toFindDto(Document document);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uploadedAt", ignore = true)
    @Mapping(target = "operation", ignore = true)
    Document toEntity(DocumentDTO dto);

    List<DocumentDTO> toDtos(List<Document> documents);
}