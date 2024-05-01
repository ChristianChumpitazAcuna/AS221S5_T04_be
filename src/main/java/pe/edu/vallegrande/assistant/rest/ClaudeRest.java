package pe.edu.vallegrande.assistant.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.assistant.model.ClaudeChat;
import pe.edu.vallegrande.assistant.service.ChatService;
import pe.edu.vallegrande.assistant.service.ClaudeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("Claude/chat")
public class ClaudeRest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ClaudeService claudeService;

    @GetMapping("/getAll")
    public Flux<ClaudeChat> findAll() {
        return chatService.findAllClaude();
    }

    @PostMapping("/sendMessage")
    public Mono<String> sendMessage(@RequestBody String userContent) {
        return claudeService.sendMessage(userContent);
    }
}