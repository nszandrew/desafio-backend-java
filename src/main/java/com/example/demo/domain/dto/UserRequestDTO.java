package com.example.demo.domain.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(String nome,
                      @Column(unique = true)
                      @Email(message = "Email deve ser no formato padrao com seuemail@dominio.com")
                      String email,
                      @Pattern(regexp = ".{6,}", message = "A senha deve ter no mínimo 6 caracteres")
                      String senha) {
}
