package com.nivedita.ai_support_assistant.util;

import com.nivedita.ai_support_assistant.model.ConversationMessage;

import java.util.List;

public class PromptBuilder {

    private PromptBuilder() {}

    public static String buildPrompt(
            String currentQuery,
            List<ConversationMessage> context
    ) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a customer support assistant.\n");
        prompt.append("Be professional, concise, and helpful.\n\n");

        if (!context.isEmpty()) {
            prompt.append("Conversation history:\n");
            for (ConversationMessage msg : context) {
                prompt.append(msg.getRole())
                        .append(": ")
                        .append(msg.getContent())
                        .append("\n");
            }
            prompt.append("\n");
        }

        prompt.append("User query:\n");
        prompt.append(currentQuery);

        return prompt.toString();
    }
}
