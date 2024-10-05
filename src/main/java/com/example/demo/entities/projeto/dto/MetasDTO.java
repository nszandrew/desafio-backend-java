package com.example.demo.entities.projeto.dto;

import java.time.LocalDate;

public record MetasDTO(String descricao, Boolean completada, LocalDate dataLimite) {
}
