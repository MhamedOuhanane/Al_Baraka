package com.albaraka.albaraka.controller;

import com.albaraka.albaraka.security.user.CustomUserDetails;
import com.albaraka.albaraka.service.interfaces.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final FileStorageService fileStorageService;

    @GetMapping("/download-secure")
    public ResponseEntity<?> downloadFile(
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
