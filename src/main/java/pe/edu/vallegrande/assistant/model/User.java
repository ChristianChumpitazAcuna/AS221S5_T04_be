package pe.edu.vallegrande.assistant.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "name")
    private String name;

    @Column(value = "email")
    private String email;

    @Column(value = "password")
    private String password;

    @Column(value = "status")
    private String status;
}
