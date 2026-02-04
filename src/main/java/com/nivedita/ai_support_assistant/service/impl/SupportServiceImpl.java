package com.nivedita.ai_support_assistant.service.impl;

import com.nivedita.ai_support_assistant.client.LlmApiClient;
import com.nivedita.ai_support_assistant.client.LlmResult;
import com.nivedita.ai_support_assistant.dto.SupportRequest;
import com.nivedita.ai_support_assistant.dto.SupportResponse;
import com.nivedita.ai_support_assistant.model.ConversationMessage;
import com.nivedita.ai_support_assistant.service.ConversationContextService;
import com.nivedita.ai_support_assistant.service.RateLimitingService;
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
    private final RateLimitingService rateLimitingService;

    public SupportServiceImpl(
            LlmApiClient llmApiClient,
            ConversationContextService contextService,
            RateLimitingService rateLimitingService
    ) {
        this.llmApiClient = llmApiClient;
        this.contextService = contextService;
        this.rateLimitingService = rateLimitingService;
    }

    @Override
    public SupportResponse processQuery(SupportRequest request) {

        // Simple client identification (can be IP / auth ID later)
        String clientId = "default-client";

        // 🔒 RATE LIMIT CHECK
        if (!rateLimitingService.isAllowed(clientId)) {
            log.warn("Rate limit exceeded for client: {}", clientId);

            return new SupportResponse(
                    "Too many requests. Please try again later.",
                    "LOW",
                    "SYSTEM"
            );
        }

        // 🔐 GDPR masking
        String maskedQuery =
                DataMaskingUtil.maskSensitiveData(request.getQuery());

        log.info("Received support query (masked): {}", maskedQuery);

        // 🧠 Conversation context
        contextService.addUserMessage(clientId, maskedQuery);
        List<ConversationMessage> context =
                contextService.getContext(clientId);

        // 🧩 Build AI prompt
        String prompt =
                PromptBuilder.buildPrompt(maskedQuery, context);

        // 🤖 AI / fallback call
        LlmResult result = llmApiClient.callLlm(prompt);

        // Store system response in context
        contextService.addSystemMessage(
                clientId,
                result.getResponse()
        );

        String confidence = result.isFromAi() ? "MEDIUM" : "LOW";
        String source = result.isFromAi() ? "AI" : "SYSTEM";

        return new SupportResponse(
                result.getResponse(),
                confidence,
                source
        );
    }
}
