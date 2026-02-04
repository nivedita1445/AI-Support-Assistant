package com.nivedita.ai_support_assistant.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_SECONDS = 60;

    private final Map<String, ClientRateState> clientState =
            new ConcurrentHashMap<>();

    public boolean isAllowed(String clientId) {

        ClientRateState state =
                clientState.computeIfAbsent(clientId, k -> new ClientRateState());

        synchronized (state) {
            long now = Instant.now().getEpochSecond();

            if (now - state.windowStart >= WINDOW_SECONDS) {
                state.windowStart = now;
                state.requestCount = 0;
            }

            state.requestCount++;

            return state.requestCount <= MAX_REQUESTS;
        }
    }

    private static class ClientRateState {
        long windowStart = Instant.now().getEpochSecond();
        int requestCount = 0;
    }
}
