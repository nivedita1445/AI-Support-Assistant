package com.nivedita.ai_support_assistant.client;

import com.nivedita.ai_support_assistant.config.AiConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class LlmApiClient {

    private final RestTemplate restTemplate;
    private final AiConfig aiConfig;

    public LlmApiClient(RestTemplate restTemplate, AiConfig aiConfig) {
        this.restTemplate = restTemplate;
        this.aiConfig = aiConfig;
    }

    public String callLlm(String prompt) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(aiConfig.getApiKey());

            Map<String, Object> body = Map.of(
                    "model", aiConfig.getModel(),
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt
                            )
                    )
            );

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://api.openai.com/v1/chat/completions",
                    request,
                    Map.class
            );

            Map<?, ?> responseBody = response.getBody();
            Map<?, ?> choice = (Map<?, ?>)
                    ((List<?>) responseBody.get("choices")).get(0);
            Map<?, ?> message = (Map<?, ?>) choice.get("message");

            return message.get("content").toString();

        } catch (Exception ex) {

            // 🔒 FALLBACK RESPONSE (NO SUBSCRIPTION REQUIRED)
            return """
            Thank you for reaching out. We understand your concern.
            Our support team is currently reviewing your request.
            Please be assured that we are here to help and will assist you shortly.
            """;
        }
    }
}
