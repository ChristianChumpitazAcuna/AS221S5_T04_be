package pe.edu.vallegrande.assistant.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.assistant.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Flux<User>findByStatus(String status);

    @Query("UPDATE users SET status = :status WHERE id = :id")
    Mono<Void> updateStatusById(Long id, String status);
}
