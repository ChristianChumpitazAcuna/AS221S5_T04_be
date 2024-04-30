package pe.edu.vallegrande.claude.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.claude.model.GeminiChat;

@Repository
public interface GeminiChatRepository extends ReactiveCrudRepository<GeminiChat, Long> {
}
