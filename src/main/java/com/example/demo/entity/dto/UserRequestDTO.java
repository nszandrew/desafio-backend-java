package com.example.demo.entity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
                      @NotNull(message = "O nome é obrigatória")
                      String nome,
                      @Email(message = "Email deve ser no formato padrao com seuemail@dominio.com")
                      @NotNull(message = "O email é obrigatória")
                      String email,
                      String senha) {
}
