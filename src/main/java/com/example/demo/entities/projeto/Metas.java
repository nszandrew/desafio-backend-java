package com.example.demo.entities.projeto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_metas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Metas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Boolean completada = false;

    private LocalDate dataLimite;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;
}
