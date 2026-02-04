package com.nivedita.ai_support_assistant.service.impl;

import com.nivedita.ai_support_assistant.client.LlmApiClient;
import com.nivedita.ai_support_assistant.client.LlmResult;
import com.nivedita.ai_support_assistant.dto.SupportRequest;
import com.nivedita.ai_support_assistant.dto.SupportResponse;
import com.nivedita.ai_support_assistant.model.ConversationMessage;
import com.nivedita.ai_support_assistant.service.ConversationContextService;
import com.nivedita.ai_support_assistant.service.SupportService;
import com.nivedita.ai_support_assistant.util.DataMaskingUtil;
import com.nivedita.ai_support_assistant.util.PromptBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportServiceImpl implements SupportService {

    private static final Logger log =
            LoggerFactory.getLogger(SupportServiceImpl.class);

    private final LlmApiClient llmApiClient;
    private final ConversationContextService contextService;

    public SupportServiceImpl(
            LlmApiClient llmApiClient,
            ConversationContextService contextService
    ) {
        this.llmApiClient = llmApiClient;
        this.contextService = contextService;
    }

    @Override
    public SupportResponse processQuery(SupportRequest request) {

        // 1️⃣ Mask sensitive data (GDPR)
        String maskedQuery =
                DataMaskingUtil.maskSensitiveData(request.getQuery());

        log.info("Received support query (masked): {}", maskedQuery);

        // 2️⃣ Identify client (simple version for now)
        String clientId = "default-client";

        // 3️⃣ Add user message to conversation context
        contextService.addUserMessage(clientId, maskedQuery);

        // 4️⃣ Fetch recent conversation context
        List<ConversationMessage> context =
                contextService.getContext(clientId);

        // 5️⃣ Build context-aware prompt
        String prompt =
                PromptBuilder.buildPrompt(maskedQuery, context);

        // 6️⃣ Call AI / fallback
        LlmResult result = llmApiClient.callLlm(prompt);

        // 7️⃣ Add system response to context
        contextService.addSystemMessage(
                clientId,
                result.getResponse()
        );

        // 8️⃣ Decide metadata
        String confidence = result.isFromAi() ? "MEDIUM" : "LOW";
        String source = result.isFromAi() ? "AI" : "SYSTEM";

        return new SupportResponse(
                result.getResponse(),
                confidence,
                source
        );
    }
}
