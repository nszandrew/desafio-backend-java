package com.example.demo.entities.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
                      @NotNull(message = "O nome é obrigatória")
                      String nome,
                      @Email(message = "Email deve ser no formato padrao com seuemail@dominio.com")
                      @NotNull(message = "O email é obrigatória")
                      String email,
                      String senha) {
}
