package com.nivedita.ai_support_assistant.client;

public class LlmResult {

    private final String response;
    private final boolean fromAi;

    private LlmResult(String response, boolean fromAi) {
        this.response = response;
        this.fromAi = fromAi;
    }

    public static LlmResult ai(String response) {
        return new LlmResult(response, true);
    }

    public static LlmResult fallback(String response) {
        return new LlmResult(response, false);
    }

    public String getResponse() {
        return response;
    }

    public boolean isFromAi() {
        return fromAi;
    }
}
