package com.example.demo.entities.projeto;

import com.example.demo.entities.projeto.dto.MetasDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Future(message = "Data limite dever ser no futuro")
    private LocalDate dataLimite;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    public Metas (MetasDTO metasDTO, Projeto projeto) {
        this.descricao = metasDTO.descricao();
        this.completada = metasDTO.completada();
        this.dataLimite = metasDTO.dataLimite();
        this.projeto = projeto;
    }

    public void update(MetasDTO data) {
        if(data.descricao() != null)this.descricao = data.descricao();
        if(data.completada() != null)this.completada = data.completada();
        if(data.dataLimite() != null)this.dataLimite = data.dataLimite();
    }
}
