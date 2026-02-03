package com.nivedita.ai_support_assistant.service.impl;

import com.nivedita.ai_support_assistant.client.LlmApiClient;
import com.nivedita.ai_support_assistant.client.LlmResult;
import com.nivedita.ai_support_assistant.dto.SupportRequest;
import com.nivedita.ai_support_assistant.dto.SupportResponse;
import com.nivedita.ai_support_assistant.service.SupportService;
import com.nivedita.ai_support_assistant.util.DataMaskingUtil;
import com.nivedita.ai_support_assistant.util.PromptBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SupportServiceImpl implements SupportService {

    private static final Logger log =
            LoggerFactory.getLogger(SupportServiceImpl.class);

    private final LlmApiClient llmApiClient;

    public SupportServiceImpl(LlmApiClient llmApiClient) {
        this.llmApiClient = llmApiClient;
    }

    @Override
    public SupportResponse processQuery(SupportRequest request) {

        String maskedQuery =
                DataMaskingUtil.maskSensitiveData(request.getQuery());

        log.info("Received support query (masked): {}", maskedQuery);

        String prompt = PromptBuilder.buildPrompt(maskedQuery);

        LlmResult result = llmApiClient.callLlm(prompt);

        String confidence = result.isFromAi() ? "MEDIUM" : "LOW";
        String source = result.isFromAi() ? "AI" : "SYSTEM";

        return new SupportResponse(
                result.getResponse(),
                confidence,
                source
        );
    }
}
