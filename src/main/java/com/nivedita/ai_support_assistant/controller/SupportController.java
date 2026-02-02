package com.nivedita.ai_support_assistant.controller;

import com.nivedita.ai_support_assistant.dto.SupportRequest;
import com.nivedita.ai_support_assistant.dto.SupportResponse;
import com.nivedita.ai_support_assistant.service.SupportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/query")
    public ResponseEntity<SupportResponse> handleQuery(
            @Valid @RequestBody SupportRequest request) {

        return ResponseEntity.ok(
                supportService.processQuery(request)
        );
    }
}
