package com.example.demo.domain.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;

public record UserRequestDTO(String nome,
                      @Column(unique = true)
                      @Email
                      String email,
                      String senha) {
}
