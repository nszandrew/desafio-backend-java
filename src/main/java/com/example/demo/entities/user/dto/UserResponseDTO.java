package com.example.demo.entities.user.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(Long id, String name, String email, LocalDateTime data) {}
