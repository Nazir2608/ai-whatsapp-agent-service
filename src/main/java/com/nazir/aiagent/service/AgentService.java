
package com.nazir.aiagent.service;

import com.nazir.aiagent.llm.LlmClient;
import com.nazir.aiagent.model.WhatsAppMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final LlmClient llmClient;

    public void processMessage(WhatsAppMessage message) {
        String response = llmClient.generate(message.getText());
        System.out.println(response);
    }
}
