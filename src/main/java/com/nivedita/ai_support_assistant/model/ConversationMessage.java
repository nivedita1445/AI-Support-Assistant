package com.nivedita.ai_support_assistant.model;

public class ConversationMessage {

    private final String role;   // USER or SYSTEM
    private final String content;

    public ConversationMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
}
