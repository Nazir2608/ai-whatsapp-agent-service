
package com.nazir.aiagent.llm;

import com.nazir.aiagent.config.AiProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component("ollamaLlmClient")
public class OllamaClient implements LlmClient {

    private final WebClient webClient;
    private final AiProperties aiProperties;

    public OllamaClient(WebClient.Builder builder, AiProperties aiProperties) {
        this.aiProperties = aiProperties;
        this.webClient = builder.baseUrl(aiProperties.getOllama().getUrl()).build();
    }

    @Override
    public String generate(String prompt) {
        String model = aiProperties.getOllama().getModel();
        String body = """
                {
                  "model": "%s",
                  "messages": [
                    {"role": "user", "content": "%s"}
                  ],
                  "stream": false
                }
                """.formatted(model, prompt);
        return webClient.post()
                .uri("/api/chat")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
