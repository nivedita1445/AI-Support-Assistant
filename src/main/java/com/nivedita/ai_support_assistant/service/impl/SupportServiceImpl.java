package com.nivedita.ai_support_assistant.service.impl;

import com.nivedita.ai_support_assistant.dto.SupportRequest;
import com.nivedita.ai_support_assistant.dto.SupportResponse;
import com.nivedita.ai_support_assistant.service.SupportService;
import org.springframework.stereotype.Service;

@Service
public class SupportServiceImpl implements SupportService {

    @Override
    public SupportResponse processQuery(SupportRequest request) {

        return new SupportResponse(
                "This is a placeholder response. AI integration will be added next.",
                "LOW",
                "SYSTEM"
        );
    }
}
