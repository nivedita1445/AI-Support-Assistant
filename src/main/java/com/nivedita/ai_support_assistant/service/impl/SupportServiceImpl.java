package com.nivedita.ai_support_assistant.service.impl;

import com.nivedita.ai_support_assistant.client.LlmApiClient;
import com.nivedita.ai_support_assistant.dto.SupportRequest;
import com.nivedita.ai_support_assistant.dto.SupportResponse;
import com.nivedita.ai_support_assistant.service.SupportService;
import com.nivedita.ai_support_assistant.util.DataMaskingUtil;
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

        // 1️⃣ GDPR STEP: Mask sensitive data (PII)
        String maskedQuery = DataMaskingUtil.maskSensitiveData(
                request.getQuery()
        );

        // 2️⃣ Build AI prompt using masked input
        String prompt = PromptBuilder.buildPrompt(maskedQuery);

        // 3️⃣ Call AI (real or fallback)
        String aiReply = llmApiClient.callLlm(prompt);

        // 4️⃣ Return stable API response (API contract frozen)
        return new SupportResponse(
                aiReply,
                "MEDIUM",
                "AI"
        );
    }
}
