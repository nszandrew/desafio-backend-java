package com.example.demo.domain;

import com.example.demo.domain.dto.UserRequestDTO;
import com.example.demo.domain.dto.UserResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    public User(UserRequestDTO data) {
        this.nome = data.nome();
        this.email = data.email();
        this.senha = data.senha();
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void updateUser(UserRequestDTO data) {
        if(data.nome() != null) this.nome = data.nome();
        if(data.email() != null) this.email = data.email();
        if(data.senha() != null) this.senha = data.senha();
        this.dataAtualizacao = LocalDateTime.now();
    }
}
