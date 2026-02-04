package com.nivedita.ai_support_assistant.service;

import com.nivedita.ai_support_assistant.model.ConversationMessage;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConversationContextService {

    private static final int MAX_HISTORY = 5;

    private final Map<String, Deque<ConversationMessage>> conversations =
            new HashMap<>();

    public List<ConversationMessage> getContext(String clientId) {
        return new ArrayList<>(
                conversations.getOrDefault(clientId, new ArrayDeque<>())
        );
    }

    public void addUserMessage(String clientId, String message) {
        addMessage(clientId, new ConversationMessage("USER", message));
    }

    public void addSystemMessage(String clientId, String message) {
        addMessage(clientId, new ConversationMessage("SYSTEM", message));
    }

    private void addMessage(String clientId, ConversationMessage msg) {
        conversations.putIfAbsent(clientId, new ArrayDeque<>());

        Deque<ConversationMessage> history = conversations.get(clientId);
        history.addLast(msg);

        if (history.size() > MAX_HISTORY) {
            history.removeFirst();
        }
    }
}
