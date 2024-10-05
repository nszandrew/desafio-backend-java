package com.example.demo.infra.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.adapters.repository.MetasRepository;
import com.example.demo.adapters.repository.ProjetoRepository;
import com.example.demo.adapters.repository.UserRepository;
import com.example.demo.entities.projeto.Projeto;
import com.example.demo.entities.projeto.dto.ProjetoRequestDTO;
import com.example.demo.entities.projeto.dto.ProjetoResponseDTO;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.custom.FieldsNotBeNullException;
import com.example.demo.exceptions.custom.ProjetoNotFoundException;
import com.example.demo.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

class ProjetoServiceImplTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private ProjetoServiceImpl projetoService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa se o projeto é criado corretamente e se os dados são retornados com sucesso.")
    void testCreateProjectSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getCredentials()).thenReturn("mockToken");
        when(tokenService.validateToken("mockToken")).thenReturn("user@test.com");

        User user = new User();
        when(userRepository.findByEmail("user@test.com")).thenReturn(user);

        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO("Projeto Teste", "Descrição Teste");

        ProjetoResponseDTO responseDTO = projetoService.createProject(projetoRequestDTO);

        assertNotNull(responseDTO);
        assertEquals("Projeto Teste", responseDTO.nome());
        assertEquals("Descrição Teste", responseDTO.descricao());
    }

    @Test
    @DisplayName("Testa se lanca exception caso o usuario nao insira nada no nome e descricao.")
    void testCreateProjectFailureMissingFields() {
        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO("", "");

        FieldsNotBeNullException exception = assertThrows(FieldsNotBeNullException.class, () -> {
            projetoService.createProject(projetoRequestDTO);
        });

        assertEquals("Campo de nome ou/e descricao precisam ser preenchidos", exception.getMessage());
    }

    @Test
    @DisplayName("Testa se a busca por ID de um projeto retorna os dados corretamente.")
    void testGetByIdSuccess() {
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");
        projeto.setDescricao("Descrição Teste");

        projeto.setMetas(Collections.emptyList());

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        ProjetoResponseDTO responseDTO = projetoService.getById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals("Projeto Teste", responseDTO.nome());
        assertEquals("Descrição Teste", responseDTO.descricao());
    }


    @Test
    @DisplayName("Testa se a exceptions lanca se ele nao encontrar por ID funciona corretamente.")
    void testGetByIdNotFound() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        ProjetoNotFoundException exception = assertThrows(ProjetoNotFoundException.class, () -> {
            projetoService.getById(1L);
        });

        assertEquals("Projeto não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Testa se a atualização de um projeto existente funciona corretamente.")
    void testUpdateProjectSuccess() {
        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO("Projeto Atualizado", "Descrição Atualizada");
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Antigo");
        projeto.setDescricao("Descrição Antiga");

        projeto.setMetas(Collections.emptyList());

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projeto);

        ProjetoResponseDTO responseDTO = projetoService.updateProject(projetoRequestDTO, 1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals("Projeto Atualizado", responseDTO.nome());
        assertEquals("Descrição Atualizada", responseDTO.descricao());
    }



    @Test
    @DisplayName("Testa a exception lanca se ele nao encontrar pelo id.")
    void testUpdateProjectNotFound() {
        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO("Projeto Atualizado", "Descrição Atualizada");

        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        ProjetoNotFoundException exception = assertThrows(ProjetoNotFoundException.class, () -> {
            projetoService.updateProject(projetoRequestDTO, 1L);
        });

        assertEquals("Projeto nao encontrado pelo id 1", exception.getMessage());
    }

    @Test
    @DisplayName("Testa se a exclusão de um projeto por ID funciona corretamente.")
    void testDeleteProjectByIdSuccess() {
        Projeto projeto = new Projeto();
        projeto.setId(1L);

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        projetoService.deleteProjectById(1L);

        verify(projetoRepository, times(1)).delete(projeto);
    }

    @Test
    @DisplayName("Testa se a exception do metodo funciona se ele nao achar no database.")
    void testDeleteProjectByIdNotFound() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        ProjetoNotFoundException exception = assertThrows(ProjetoNotFoundException.class, () -> {
            projetoService.deleteProjectById(1L);
        });

        assertEquals("Projeto nao pode ser encontrado pelo id fornecido", exception.getMessage());
    }
}