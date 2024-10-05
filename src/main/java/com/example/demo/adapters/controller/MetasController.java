package com.example.demo.adapters.controller;

import com.example.demo.adapters.service.MetasService;
import com.example.demo.entities.projeto.dto.MetasDTO;
import com.example.demo.entities.projeto.dto.MetasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metas")
@Tag(name = "Metas")
public class MetasController {

    private final MetasService metasService;

    public MetasController(MetasService metasService) {
        this.metasService = metasService;
    }


    @Operation(summary = "Cria uma nova meta para o projeto especificado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição (campos inválidos)"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
    })
    @PostMapping("/{id}")
    public ResponseEntity<MetasResponseDTO> createMetas(@RequestBody @Valid MetasDTO metasDTO, @PathVariable Long id) {
        var metas = metasService.createMeta(metasDTO, id);
        return new ResponseEntity<>(metas, HttpStatus.CREATED);
    }


    @Operation(summary = "Recupera uma meta pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meta recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<MetasResponseDTO> getMetas(@PathVariable Long id) {
        var metas = metasService.getById(id);
        return new ResponseEntity<>(metas, HttpStatus.OK);
    }


    @Operation(summary = "Atualiza uma meta existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição (campos inválidos)"),
            @ApiResponse(responseCode = "404", description = "Meta ou projeto não encontrados"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<MetasResponseDTO> updateMetas(@RequestBody @Valid MetasDTO metasDTO, @PathVariable Long id) {
        var metas = metasService.updateMeta(metasDTO, id);
        return new ResponseEntity<>(metas, HttpStatus.OK);
    }

    @Operation(summary = "Exclui uma meta pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Meta excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMetas(@PathVariable Long id) {
        metasService.deleteMeta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
