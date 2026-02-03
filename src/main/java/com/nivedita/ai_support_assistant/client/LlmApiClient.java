package com.nivedita.ai_support_assistant.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LlmApiClient {

    private static final Logger log =
            LoggerFactory.getLogger(LlmApiClient.class);

    public LlmResult callLlm(String prompt) {
        try {
            // 🔹 Simulated AI call (or real API if key exists)
            String aiResponse =
                    "Thank you for reaching out. We understand your concern.\n" +
                            "Our support team is currently reviewing your request.\n" +
                            "Please be assured that we are here to help and will assist you shortly.\n";

            return LlmResult.ai(aiResponse);

        } catch (Exception ex) {
            log.warn("AI service unavailable, switching to fallback");

            String fallbackResponse =
                    "We have received your request and our support team will assist you shortly.";

            return LlmResult.fallback(fallbackResponse);
        }
    }
}
