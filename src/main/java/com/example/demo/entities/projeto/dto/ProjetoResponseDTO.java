package com.example.demo.entities.projeto.dto;

import com.example.demo.entities.user.User;

public record ProjetoResponseDTO(Long id, String nome, String descricao, java.time.LocalDateTime dataCriacao,
                                 java.time.LocalDateTime criacao,
                                 java.util.List<com.example.demo.entities.projeto.Metas> metas) {
}
