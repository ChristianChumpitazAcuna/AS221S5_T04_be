package pe.edu.vallegrande.claude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.vallegrande.claude.model.Chat;
import pe.edu.vallegrande.claude.service.ChatService;
import pe.edu.vallegrande.claude.service.ClaudeService;


import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ClaudeService claudeService;

    @RequestMapping("/getAll")
    public ResponseEntity<List<Chat>> getAll() {
        return ResponseEntity.ok(chatService.getAll());
    }

    @PostMapping
    public String sendMessage(@RequestBody String userContent) {
        return claudeService.sendMessage(userContent);
    }
}