package com.example.demo.entity.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(Long id, String name, String email, LocalDateTime data) {}
