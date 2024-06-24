package pe.edu.vallegrande.assistant.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.assistant.model.GeminiChat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface GeminiChatRepository extends ReactiveCrudRepository<GeminiChat, Long> {
    Flux<GeminiChat> findByUserIdAndStatusOrderById(UUID userId, String status);

    Mono<GeminiChat> findByIdAndUserIdAndStatus(Long id, UUID userId, String status);
}
