package com.nivedita.ai_support_assistant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupportRequest {

    @NotBlank(message = "Query must not be empty")
    private String query;

    private String customerId;
}
