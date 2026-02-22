package com.nazir.aiagent.llm;

import com.nazir.aiagent.config.AiProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("openAiLlmClient")
public class OpenAiClient implements LlmClient {

    private final WebClient webClient;
    private final AiProperties aiProperties;

    public OpenAiClient(WebClient.Builder builder, AiProperties aiProperties) {
        this.aiProperties = aiProperties;
        this.webClient = builder.baseUrl(aiProperties.getOpenai().getUrl()).build();
    }

    @Override
    public String generate(String prompt) {
        String apiKey = aiProperties.getOpenai().getApiKey();
        String model = aiProperties.getOpenai().getModel();
        double temperature = aiProperties.getOpenai().getTemperature();
        String body = """
                {
                  "model": "%s",
                  "messages": [
                    {"role": "user", "content": "%s"}
                  ],
                  "temperature": %s
                }
                """.formatted(model, prompt, Double.toString(temperature));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(ex -> Mono.error(new IllegalStateException("OpenAI call failed", ex)))
                .block();
    }
}

