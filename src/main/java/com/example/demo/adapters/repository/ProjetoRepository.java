package com.example.demo.adapters.repository;

import com.example.demo.entities.projeto.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
