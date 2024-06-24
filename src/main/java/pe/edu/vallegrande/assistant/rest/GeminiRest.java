package pe.edu.vallegrande.assistant.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.assistant.model.GeminiChat;
import pe.edu.vallegrande.assistant.service.ChatService;
import pe.edu.vallegrande.assistant.service.GeminiService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("Gemini")
public class GeminiRest {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/{status}/{userId}")
    public ResponseEntity<Flux<GeminiChat>> findAll(@PathVariable UUID userId, @PathVariable String status) {
        return ResponseEntity.ok(chatService.findAllGemini(userId, status));
    }

    @PostMapping("/sendMessage/{userId}")
    public ResponseEntity<Mono<String>> save(@PathVariable UUID userId, @RequestBody String userMessage) {
        return ResponseEntity.ok(geminiService.sendMessage(userId, userMessage));
    }

    @PutMapping("/update/{userId}/{id}")
    public ResponseEntity<Mono<String>> update(@PathVariable Long id, @PathVariable UUID userId, @RequestBody String userMessage) {
        return ResponseEntity.ok(geminiService.updateSendMessage(id, userId, userMessage));
    }

    @PutMapping("/changeStatus/{status}/{id}")
    public ResponseEntity<Mono<GeminiChat>> restore(@PathVariable Long id, @PathVariable String status) {
        return ResponseEntity.ok(geminiService.changeStatus(id, status));
    }
}
