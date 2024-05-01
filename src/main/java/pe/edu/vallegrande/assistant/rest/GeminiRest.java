package pe.edu.vallegrande.assistant.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.assistant.model.GeminiChat;
import pe.edu.vallegrande.assistant.service.ChatService;
import pe.edu.vallegrande.assistant.service.GeminiService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("Gemini/chat")
public class GeminiRest {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/getAll")
    public Flux<GeminiChat> findAll() {
        return chatService.findAllGemini();
    }

    @PostMapping("/sendMessage")
    public Mono<String> sendMessage(@RequestBody String userContent) {
        return geminiService.sendMessage(userContent);
    }

}
