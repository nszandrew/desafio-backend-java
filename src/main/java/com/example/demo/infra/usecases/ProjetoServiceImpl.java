package com.example.demo.infra.usecases;

import com.example.demo.adapters.repository.ProjetoRepository;
import com.example.demo.adapters.repository.UserRepository;
import com.example.demo.adapters.service.ProjetoService;
import com.example.demo.entities.projeto.Projeto;
import com.example.demo.entities.projeto.dto.ProjetoDTO;
import com.example.demo.entities.projeto.dto.ProjetoResponseDTO;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.custom.ProjetoNotFoundException;
import com.example.demo.exceptions.custom.UserNotFoundException;
import com.example.demo.infra.security.TokenService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository repository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(ProjetoServiceImpl.class);
    private final TokenService tokenService;

    public ProjetoServiceImpl(ProjetoRepository repository, UserRepository userRepository, TokenService tokenService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }


    @Override
    @Transactional
    public ProjetoResponseDTO createProject(ProjetoDTO data) {
        logger.info("Criando um novo projeto {}", data.nome());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var jwtToken = (String) authentication.getCredentials();
        String email = tokenService.validateToken(jwtToken);
        User user = (User) userRepository.findByEmail(email);

        Projeto projeto = new Projeto(data, user);
        repository.save(projeto);

        return new ProjetoResponseDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao(), projeto.getDataCriacao(), projeto.getDataAtualizacao(), projeto.getMetas());
    }

    @Override
    @Transactional
    public ProjetoResponseDTO updateProject(ProjetoDTO data, Long id) {
        logger.info("Atualizando um projeto");
        var projeto = repository.findById(id).orElseThrow(() -> new ProjetoNotFoundException("Projeto nao encontrado pelo id " + id));
        projeto.updateProjeto(data);
        repository.save(projeto);
        return new ProjetoResponseDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao(), projeto.getDataCriacao(), projeto.getDataAtualizacao(), projeto.getMetas());
    }

    @Override
    public ProjetoResponseDTO getById(Long id) {
        var data = repository.findById(id).get();
        ProjetoResponseDTO dto = new ProjetoResponseDTO(data.getId(), data.getNome(), data.getDescricao(), data.getDataCriacao(), data.getDataCriacao(), data.getMetas());
        return dto;
    }

    @Override
    public void deleteProjectById(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {throw new ProjetoNotFoundException("Projeto nao pode ser encontrado pelo id fornecido");});
    }
}
