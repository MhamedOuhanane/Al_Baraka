package com.albaraka.albaraka.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileStorageService {
    String uploadDocument(MultipartFile file, UUID documentUuid, UUID ownerUuid);
    String getDocumentUrl(String filePath, UUID ownerUuid, String role);
}
