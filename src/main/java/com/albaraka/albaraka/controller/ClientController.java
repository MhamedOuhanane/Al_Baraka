package com.albaraka.albaraka.controller;

import com.albaraka.albaraka.model.dto.document.DocumentCreateDTO;
import com.albaraka.albaraka.model.dto.operation.OperationCreateDTO;
import com.albaraka.albaraka.security.user.CustomUserDetails;
import com.albaraka.albaraka.service.interfaces.DocumentService;
import com.albaraka.albaraka.service.interfaces.OperationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final OperationService operationService;
    private final DocumentService documentService;

    @GetMapping("/operations")
    public ResponseEntity<?> showsOperation(
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        var operations = operationService.findAllByClient(userDetails.getUuid());
        return ResponseEntity.ok(
                Map.of(
                        "message", "Trouver vous operations: ",
                        "data", operations,
                        "path", request.getRequestURI()
                )
        );
    }

    @PostMapping("/operations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> createOperation(
            @Valid @RequestBody OperationCreateDTO dto,
            HttpServletRequest request
    ) {
        var operation = operationService.create(dto);

        return ResponseEntity.ok(
                Map.of(
                        "message", "L'opération a été un succès.",
                        "data", operation,
                        "path", request.getRequestURI()
                )
        );
    }

    @PostMapping("/operations/{uuid}/document")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> createDocument(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody DocumentCreateDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        dto.setOperationUuid(uuid);
        var document = documentService.create(file, userDetails.getUuid(), dto);

        return ResponseEntity.ok(
                Map.of(
                        "message", "La document ajouter avec succès.",
                        "data", document,
                        "path", request.getRequestURI()
                )
        );
    }


}
