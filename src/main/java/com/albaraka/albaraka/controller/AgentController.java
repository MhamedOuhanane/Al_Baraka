package com.albaraka.albaraka.controller;

import com.albaraka.albaraka.model.entity.Operation;
import com.albaraka.albaraka.model.enums.OperationStatus;
import com.albaraka.albaraka.security.user.CustomUserDetails;
import com.albaraka.albaraka.service.interfaces.OperationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {
    private final OperationService operationService;

    @GetMapping("/operations/{status}")
    public ResponseEntity<?> showsOperation(
            @PathVariable("status") OperationStatus status,
            HttpServletRequest request
    ) {
        var operations = operationService.findAll().stream()
                .filter(operation -> operation.getStatus().equals(status))
                .toList();
        return ResponseEntity.ok(
                Map.of(
                        "message", "Trouver les operations avec le status [" + status.toString() + "]: ",
                        "data", operations,
                        "path", request.getRequestURI()
                )
        );
    }

    @PatchMapping("/operation/{uuid}/{status}")
    public ResponseEntity<?> showsOperation(
            @PathVariable("uuid")UUID uuid,
            @PathVariable("status") OperationStatus status,
            HttpServletRequest request
    ) {
        var operation = operationService.updateStatus(uuid, status);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Modifier l'operations: ",
                        "data", operation,
                        "path", request.getRequestURI()
                )
        );
    }
}
