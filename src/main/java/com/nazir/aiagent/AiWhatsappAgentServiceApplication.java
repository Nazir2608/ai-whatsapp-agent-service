
package com.nazir.aiagent;

import com.nazir.aiagent.config.AiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AiProperties.class)
public class AiWhatsappAgentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiWhatsappAgentServiceApplication.class, args);
    }
}
