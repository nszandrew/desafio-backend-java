package com.example.demo.adapters.controller;

import com.example.demo.adapters.service.ProjetoService;
import com.example.demo.entities.projeto.dto.ProjetoDTO;
import com.example.demo.entities.projeto.dto.ProjetoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projeto")
public class ProjetoController {

    private final ProjetoService service;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> save(@RequestBody @Valid ProjetoDTO data){
        var createProjeto = service.createProject(data);
        return new ResponseEntity<>(createProjeto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> getById(@PathVariable Long id){
        var projeto = service.getById(id);
        return ResponseEntity.ok(projeto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProjetoDTO data){
        var updateProject = service.updateProject(data, id);
        return ResponseEntity.ok(updateProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }
}
