package pe.edu.vallegrande.claude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.claude.model.Chat;
import pe.edu.vallegrande.claude.repository.ChatRepository;


import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    // listar chats
    public List<Chat> getAll() {
        return chatRepository.findAll();
    }
}

