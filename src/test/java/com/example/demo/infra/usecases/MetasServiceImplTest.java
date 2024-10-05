package com.example.demo.infra.usecases;

import com.example.demo.adapters.repository.MetasRepository;
import com.example.demo.adapters.repository.ProjetoRepository;
import com.example.demo.entities.projeto.Metas;
import com.example.demo.entities.projeto.Projeto;
import com.example.demo.entities.projeto.dto.MetasDTO;
import com.example.demo.entities.projeto.dto.MetasResponseDTO;
import com.example.demo.exceptions.custom.FieldsNotBeNullException;
import com.example.demo.exceptions.custom.MetaNotFoundException;
import com.example.demo.exceptions.custom.ProjetoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MetasServiceImplTest {

    @Mock
    private MetasRepository metasRepository;

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private MetasServiceImpl metasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a criação de uma nova meta com sucesso.")
    void testCreateMetaSuccess() {
        MetasDTO metasDTO = new MetasDTO("Meta Teste", true, LocalDate.now());

        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");

        projeto.setMetas(new ArrayList<>());

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(metasRepository.save(any(Metas.class))).thenAnswer(i -> i.getArguments()[0]);

        MetasResponseDTO responseDTO = metasService.createMeta(metasDTO, 1L);

        assertNotNull(responseDTO);
        assertEquals("Meta Teste", responseDTO.data().descricao());
    }


    @Test
    @DisplayName("Testa se a criação de uma nova meta falha quando os campos obrigatórios não são preenchidos.")
    void testCreateMetaFailureMissingFields() {
        MetasDTO metasDTO = new MetasDTO("", false, null);

        FieldsNotBeNullException exception = assertThrows(FieldsNotBeNullException.class, () -> {
            metasService.createMeta(metasDTO, 1L);
        });

        assertEquals("Campo de data limite ou/e descricao precisam ser preenchidos", exception.getMessage());
    }

    @Test
    @DisplayName("Testa a atualização de uma meta existente com sucesso.")
    void testUpdateMetaSuccess() {
        MetasDTO metasDTO = new MetasDTO("Meta Atualizada", true, null);
        Metas metas = new Metas();
        metas.setId(1L);
        metas.setDescricao("Meta Antiga");

        when(metasRepository.findById(1L)).thenReturn(Optional.of(metas));
        when(metasRepository.save(any(Metas.class))).thenAnswer(i -> i.getArguments()[0]);

        MetasResponseDTO responseDTO = metasService.updateMeta(metasDTO, 1L);

        assertNotNull(responseDTO);
        assertEquals("Meta Atualizada", responseDTO.data().descricao());
    }

    @Test
    @DisplayName("Testa se a atualização falha quando a meta não é encontrada pelo ID.")
    void testUpdateMetaNotFound() {
        MetasDTO metasDTO = new MetasDTO("Meta Atualizada", true, null);

        when(metasRepository.findById(1L)).thenReturn(Optional.empty());
        MetaNotFoundException exception = assertThrows(MetaNotFoundException.class, () -> {
            metasService.updateMeta(metasDTO, 1L);
        });

        assertEquals("Meta nao pode ser encontrado pelo id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Testa a busca de uma meta por ID com sucesso.")
    void testGetByIdSuccess() {
        Metas metas = new Metas();
        metas.setId(1L);
        metas.setDescricao("Meta Teste");

        when(metasRepository.findById(1L)).thenReturn(Optional.of(metas));

        MetasResponseDTO responseDTO = metasService.getById(1L);

        assertNotNull(responseDTO);
        assertEquals("Meta Teste", responseDTO.data().descricao());
    }

    @Test
    @DisplayName("Testa se a busca falha quando a meta não é encontrada pelo ID.")
    void testGetByIdNotFound() {
        when(metasRepository.findById(1L)).thenReturn(Optional.empty());

        ProjetoNotFoundException exception = assertThrows(ProjetoNotFoundException.class, () -> {
            metasService.getById(1L);
        });

        assertEquals("Meta não encontrada pelo id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Testa a exclusão de uma meta por ID com sucesso.")
    void testDeleteMetaSuccess() {
        Metas metas = new Metas();
        metas.setId(1L);

        when(metasRepository.findById(1L)).thenReturn(Optional.of(metas));

        metasService.deleteMeta(1L);

        verify(metasRepository, times(1)).delete(metas);
    }

    @Test
    @DisplayName("Testa se a exclusão falha quando a meta não é encontrada pelo ID.")
    void testDeleteMetaNotFound() {
        when(metasRepository.findById(1L)).thenReturn(Optional.empty());

        MetaNotFoundException exception = assertThrows(MetaNotFoundException.class, () -> {
            metasService.deleteMeta(1L);
        });

        assertEquals("Meta nao encontrada pelo id: 1", exception.getMessage());
    }
}