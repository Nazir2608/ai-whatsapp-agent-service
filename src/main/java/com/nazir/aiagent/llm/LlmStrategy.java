package com.nazir.aiagent.llm;

import com.nazir.aiagent.config.AiProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class LlmStrategy implements LlmClient {

    private final AiProperties aiProperties;
    private final LlmClient ollamaLlmClient;
    private final LlmClient openAiLlmClient;

    public LlmStrategy(AiProperties aiProperties, @Qualifier("ollamaLlmClient") LlmClient ollamaLlmClient,
                       @Qualifier("openAiLlmClient") LlmClient openAiLlmClient) {
        this.aiProperties = aiProperties;
        this.ollamaLlmClient = ollamaLlmClient;
        this.openAiLlmClient = openAiLlmClient;
    }

    @Override
    public String generate(String prompt) {
        String provider = aiProperties.getProvider();
        if ("openai".equalsIgnoreCase(provider)) {
            return openAiLlmClient.generate(prompt);
        }
        return ollamaLlmClient.generate(prompt);
    }
}
