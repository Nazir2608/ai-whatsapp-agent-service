
package com.nazir.aiagent.controller;

import com.nazir.aiagent.model.WhatsAppMessage;
import com.nazir.aiagent.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<Void> receive(@RequestBody WhatsAppMessage message) {
        agentService.processMessage(message);
        return ResponseEntity.ok().build();
    }
}
