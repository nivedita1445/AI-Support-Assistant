package com.nivedita.ai_support_assistant.service.impl;

import com.nivedita.ai_support_assistant.client.LlmApiClient;
import com.nivedita.ai_support_assistant.dto.SupportRequest;
import com.nivedita.ai_support_assistant.dto.SupportResponse;
import com.nivedita.ai_support_assistant.service.SupportService;
import com.nivedita.ai_support_assistant.util.PromptBuilder;
import org.springframework.stereotype.Service;

@Service
public class SupportServiceImpl implements SupportService {

    private final LlmApiClient llmApiClient;

    public SupportServiceImpl(LlmApiClient llmApiClient) {
        this.llmApiClient = llmApiClient;
    }

    @Override
    public SupportResponse processQuery(SupportRequest request) {

        String prompt = PromptBuilder.buildPrompt(request.getQuery());
        String aiReply = llmApiClient.callLlm(prompt);

        return new SupportResponse(
                aiReply,
                "MEDIUM",
                "AI"
        );
    }
}
