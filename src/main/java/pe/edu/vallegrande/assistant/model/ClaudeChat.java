package pe.edu.vallegrande.assistant.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "claude")
@Getter
@Setter
public class ClaudeChat {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "message")
    private String message;

    @Column(value = "response")
    private String response;
}