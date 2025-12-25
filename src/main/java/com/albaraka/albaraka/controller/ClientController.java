package com.albaraka.albaraka.controller;

import com.albaraka.albaraka.security.user.CustomUserDetails;
import com.albaraka.albaraka.service.interfaces.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final FileStorageService fileStorageService;

    @GetMapping("/operations")
    public ResponseEntity<?> showsOperation(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok("{}");
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        String message = fileStorageService.uploadDocument(file, UUID.randomUUID(), userDetails.getUuid());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/download-secure")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("path") String filePath,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        InputStreamResource resource = fileStorageService.getFileAsResource(
                filePath, userDetails.getUuid(), userDetails.getRole());
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

        String contentType = determineContentType(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }

    private String determineContentType(String filePath) {
        if (filePath.endsWith(".pdf")) return "application/pdf";
        if (filePath.endsWith(".png")) return "image/png";
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
        if (filePath.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }
}
