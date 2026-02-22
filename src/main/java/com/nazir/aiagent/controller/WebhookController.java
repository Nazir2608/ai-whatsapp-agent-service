
package com.nazir.aiagent.controller;

import com.nazir.aiagent.model.WhatsAppMessage;
import com.nazir.aiagent.service.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<Void> receive(@RequestBody WhatsAppMessage message) {
        log.info("webhook received..");
        agentService.processMessage(message);
        return ResponseEntity.ok().build();
    }
}
