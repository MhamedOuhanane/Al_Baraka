package com.albaraka.albaraka.service.impl;

import com.albaraka.albaraka.exception.generic.AuthorizationException;
import com.albaraka.albaraka.exception.generic.ResourceNotFoundException;
import com.albaraka.albaraka.model.dto.document.DocumentCreateDTO;
import com.albaraka.albaraka.model.dto.document.DocumentDTO;
import com.albaraka.albaraka.model.dto.document.DocumentFindDTO;
import com.albaraka.albaraka.model.entity.Document;
import com.albaraka.albaraka.model.entity.Operation;
import com.albaraka.albaraka.model.mapper.DocumentMapper;
import com.albaraka.albaraka.repository.DocumentRepository;
import com.albaraka.albaraka.repository.OperationRepository;
import com.albaraka.albaraka.service.interfaces.DocumentService;
import com.albaraka.albaraka.service.interfaces.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository repository;
    private final DocumentMapper mapper;
    private final FileStorageService fileStorageService;
    private final OperationRepository operationRepository;

    @Override
    @Transactional
    public DocumentFindDTO create(MultipartFile file, UUID ownerUuid, DocumentCreateDTO dto) {
        Operation operation = operationRepository.findByUuid(dto.getOperationUuid())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "L'operation avec l'uuid '" + dto.getOperationUuid() + "' n'existe pas !"
                ));

        if (!ownerUuid.equals(operation.getAccountSource().getUser().getUuid())) {
            throw new AuthorizationException("Vous n'avez pas autorisation d'ajouter un document à cette opération !");
        }

        Document document = Document.builder()
                .uuid(UUID.randomUUID())
                .fileName(UUID.randomUUID().toString())
                .fileType(file.getContentType())
                .operation(operation)
                .build();

        String storagePath = fileStorageService.uploadDocument(file, document.getUuid(), ownerUuid);

        document.setStoragePath(storagePath);

        repository.save(document);

        return mapper.toFindDto(document);
    }

    @Override
    public List<DocumentDTO> findAll() {
        List<Document> documents = repository.findAll();

        return mapper.toDtos(documents);
    }

    @Override
    public DocumentFindDTO find(UUID uuid) {
        Document document = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La document avec l'uuid '" + uuid + "' n'existe pas !"
                ));

        var user = document.getOperation().getAccountSource().getUser();

        return mapper.toFindDto(document);
    }
}
