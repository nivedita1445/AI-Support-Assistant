package com.nivedita.ai_support_assistant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SupportResponse {

    private String response;
    private String confidence;
    private String source;
}
