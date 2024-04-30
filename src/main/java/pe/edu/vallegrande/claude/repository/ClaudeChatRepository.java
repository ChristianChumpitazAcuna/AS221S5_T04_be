package pe.edu.vallegrande.claude.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.claude.model.ClaudeChat;

@Repository
public interface ClaudeChatRepository extends ReactiveCrudRepository<ClaudeChat, Long> {
}
