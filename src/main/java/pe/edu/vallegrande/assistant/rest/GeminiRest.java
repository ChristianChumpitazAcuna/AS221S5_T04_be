package pe.edu.vallegrande.assistant.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.assistant.model.GeminiChat;
import pe.edu.vallegrande.assistant.service.ChatService;
import pe.edu.vallegrande.assistant.service.GeminiService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("Gemini")
public class GeminiRest {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/actives/{userId}")
    public Flux<GeminiChat> findActives(@PathVariable Long userId) {
        return chatService.findAllGemini(userId, "A");
    }

    @GetMapping("/inactives/{userId}")
    public Flux<GeminiChat> findInactives(@PathVariable Long userId) {
        return chatService.findAllGemini(userId, "I");
    }

    @PostMapping("/sendMessage/{userId}")
    public Mono<String> save(@PathVariable Long userId, @RequestBody String userMessage) {
        return geminiService.sendMessage(userId, userMessage);
    }

    @PutMapping("/update/{userId}/{id}")
    public Mono<String> update(@PathVariable Long id, @PathVariable Long userId, @RequestBody String userMessage) {
        return geminiService.updateSendMessage(id, userId, userMessage);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Mono<GeminiChat>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(geminiService.changeStatus(id, "I"));
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<Mono<GeminiChat>> restore(@PathVariable Long id) {
        return ResponseEntity.ok(geminiService.changeStatus(id, "A"));
    }
}
