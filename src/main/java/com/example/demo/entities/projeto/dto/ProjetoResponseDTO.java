package com.example.demo.entities.projeto.dto;

import java.util.List;

public record ProjetoResponseDTO(Long id, String nome, String descricao, java.time.LocalDateTime dataCriacao,
                                 java.time.LocalDateTime dataUpdate,
                                 List<MetasDTO> metas) {}
