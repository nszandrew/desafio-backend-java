package com.example.demo.entity.dto;

import jakarta.validation.constraints.NotNull;

public record AuthDTO(
        @NotNull(message = "O email é obrigatória")
        String email,
        @NotNull(message = "A senha é obrigatória")
        String senha) {}
