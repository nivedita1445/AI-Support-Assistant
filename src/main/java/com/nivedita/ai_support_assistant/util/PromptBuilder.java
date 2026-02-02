package com.nivedita.ai_support_assistant.util;

public class PromptBuilder {

    public static String buildPrompt(String userQuery) {

        return """
        You are a professional customer support assistant.
        Follow these rules:
        - Be polite and professional
        - Do not ask for sensitive information
        - Do not hallucinate facts
        - Keep responses short and clear

        Customer Query:
        %s
        """.formatted(userQuery);
    }
}
