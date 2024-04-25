package pe.edu.vallegrande.claude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.vallegrande.claude.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
