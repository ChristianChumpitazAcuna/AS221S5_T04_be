package pe.edu.vallegrande.assistant.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.assistant.model.ClaudeChat;

@Repository
public interface ClaudeChatRepository extends ReactiveCrudRepository<ClaudeChat, Long> {
}
