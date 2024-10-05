package com.example.demo.adapters.service;

import com.example.demo.entities.projeto.dto.ProjetoRequestDTO;
import com.example.demo.entities.projeto.dto.ProjetoResponseDTO;

public interface ProjetoService {

    ProjetoResponseDTO createProject(ProjetoRequestDTO data);
    ProjetoResponseDTO updateProject(ProjetoRequestDTO data, Long id);
    ProjetoResponseDTO getById(Long id);
    void deleteProjectById(Long id);
}
