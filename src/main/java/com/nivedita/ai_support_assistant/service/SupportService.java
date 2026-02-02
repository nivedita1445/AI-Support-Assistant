package com.nivedita.ai_support_assistant.service;

import com.nivedita.ai_support_assistant.dto.SupportRequest;
import com.nivedita.ai_support_assistant.dto.SupportResponse;

public interface SupportService {

    SupportResponse processQuery(SupportRequest request);
}
