package com.example.demo.adapters.controller;

import com.example.demo.adapters.service.MetasService;
import com.example.demo.entities.projeto.dto.MetasDTO;
import com.example.demo.entities.projeto.dto.MetasResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metas")
public class MetasController {

    private final MetasService metasService;

    public MetasController(MetasService metasService) {
        this.metasService = metasService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<MetasResponseDTO> createMetas(@RequestBody @Valid MetasDTO metasDTO, @PathVariable Long id) {
        var metas = metasService.createMeta(metasDTO, id);
        return new ResponseEntity<>(metas, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetasResponseDTO> getMetas(@PathVariable Long id) {
        var metas = metasService.getById(id);
        return new ResponseEntity<>(metas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetasResponseDTO> updateMetas(@RequestBody @Valid MetasDTO metasDTO, @PathVariable Long id) {
        var metas = metasService.updateMeta(metasDTO, id);
        return new ResponseEntity<>(metas, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMetas(@PathVariable Long id) {
        metasService.deleteMeta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
