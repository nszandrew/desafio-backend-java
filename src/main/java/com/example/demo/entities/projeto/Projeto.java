package com.example.demo.entities.projeto;

import com.example.demo.entities.projeto.dto.ProjetoRequestDTO;
import com.example.demo.entities.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_projeto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metas> metas;

    public Projeto(ProjetoRequestDTO dto, User user){
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.user = user;
    }

    public void updateProjeto(ProjetoRequestDTO dto){
        if(dto.nome() != null)this.nome = dto.nome();
        if(dto.descricao() != null)this.descricao = dto.descricao();
        this.dataAtualizacao = LocalDateTime.now();
    }
}
