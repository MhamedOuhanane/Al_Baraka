package com.albaraka.albaraka.service.interfaces;

import com.albaraka.albaraka.model.dto.document.DocumentCreateDTO;
import com.albaraka.albaraka.model.dto.document.DocumentDTO;
import com.albaraka.albaraka.model.dto.document.DocumentFindDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentService {
    DocumentFindDTO create(MultipartFile file, UUID ownerUuid, DocumentCreateDTO dto);
    List<DocumentDTO> findAll();
    DocumentFindDTO find(UUID uuid);
}
