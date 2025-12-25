package com.albaraka.albaraka.controller;

import com.albaraka.albaraka.security.user.CustomUserDetails;
import com.albaraka.albaraka.service.interfaces.OperationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final OperationService operationService;

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


}
