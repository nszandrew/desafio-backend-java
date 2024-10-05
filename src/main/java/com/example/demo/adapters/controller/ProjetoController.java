package com.example.demo.adapters.controller;

import com.example.demo.adapters.service.ProjetoService;
import com.example.demo.entities.projeto.dto.ProjetoRequestDTO;
import com.example.demo.entities.projeto.dto.ProjetoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projeto")
@Tag(name = "Projetos")
public class ProjetoController {

    private final ProjetoService service;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @Operation(summary = "Cria um novo projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição (campos inválidos)"),
    })
    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> save(@RequestBody @Valid ProjetoRequestDTO data){
        var createProjeto = service.createProject(data);
        return new ResponseEntity<>(createProjeto, HttpStatus.CREATED);
    }

    @Operation(summary = "Recupera um projeto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto recuperado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> getById(@PathVariable Long id){
        var projeto = service.getById(id);
        return ResponseEntity.ok(projeto);
    }

    @Operation(summary = "Atualiza um projeto existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição (campos inválidos)"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProjetoRequestDTO data){
        var updateProject = service.updateProject(data, id);
        return ResponseEntity.ok(updateProject);
    }

    @Operation(summary = "Exclui um projeto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Projeto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }
}
