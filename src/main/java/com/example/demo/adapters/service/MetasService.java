package com.example.demo.adapters.service;

import com.example.demo.entities.projeto.dto.MetasDTO;
import com.example.demo.entities.projeto.dto.MetasResponseDTO;

public interface MetasService {

    MetasResponseDTO createMeta(MetasDTO data, Long id);
    MetasResponseDTO updateMeta(MetasDTO data, Long id);
    MetasResponseDTO getById(Long id);
    void deleteMeta(Long id);
}
