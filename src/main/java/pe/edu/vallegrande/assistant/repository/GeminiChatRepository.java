package pe.edu.vallegrande.assistant.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.assistant.model.GeminiChat;
import reactor.core.publisher.Flux;

@Repository
public interface GeminiChatRepository extends ReactiveCrudRepository<GeminiChat, Long> {
    Flux<GeminiChat> findByUserIdAndStatusOrderById(Long id, String status);
}
