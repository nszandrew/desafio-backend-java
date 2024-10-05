package com.example.demo.adapters.service;

import com.example.demo.entities.projeto.dto.ProjetoDTO;
import com.example.demo.entities.projeto.dto.ProjetoResponseDTO;

public interface ProjetoService {

    ProjetoResponseDTO createProject(ProjetoDTO data);
    ProjetoResponseDTO updateProject(ProjetoDTO data, Long id);
    ProjetoResponseDTO getById(Long id);
    void deleteProjectById(Long id);
}
