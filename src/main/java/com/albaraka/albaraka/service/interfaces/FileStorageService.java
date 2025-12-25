package com.albaraka.albaraka.service.interfaces;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileStorageService {
    String uploadDocument(MultipartFile file, UUID documentUuid, UUID ownerUuid);
    InputStreamResource getFileAsResource(String filePath, UUID ownerUuid, String role);
}
