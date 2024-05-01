package pe.edu.vallegrande.assistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.assistant.model.ClaudeChat;
import pe.edu.vallegrande.assistant.model.GeminiChat;
import pe.edu.vallegrande.assistant.repository.ClaudeChatRepository;
import pe.edu.vallegrande.assistant.repository.GeminiChatRepository;
import reactor.core.publisher.Flux;


@Service
public class ChatService {

    @Autowired
    private ClaudeChatRepository chatRepository;

    @Autowired
    private GeminiChatRepository geminiChatRepository;

    public Flux<ClaudeChat> findAllClaude() {
        return chatRepository.findAll();
    }

    public Flux<GeminiChat> findAllGemini() {
        return geminiChatRepository.findAll();
    }
}

