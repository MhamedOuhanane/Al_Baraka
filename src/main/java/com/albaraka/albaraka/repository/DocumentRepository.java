package com.albaraka.albaraka.repository;

import com.albaraka.albaraka.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    Optional<Document> findByUuid(UUID uuid);
    Optional<Document> findByFileName(String fileName);
    List<Document> findByFileType(String fileType);
}
