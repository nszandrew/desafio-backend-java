package com.example.demo.domain.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(String name, String email, LocalDateTime data) {}
