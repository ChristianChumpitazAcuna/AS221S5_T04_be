package pe.edu.vallegrande.claude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.claude.model.Chat;
import pe.edu.vallegrande.claude.repository.ChatRepository;
import reactor.core.publisher.Flux;


@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Flux<Chat> findAll() {
        return chatRepository.findAll();
    }
}

