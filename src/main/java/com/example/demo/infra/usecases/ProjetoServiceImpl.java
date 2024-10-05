package com.example.demo.infra.usecases;

import com.example.demo.adapters.repository.MetasRepository;
import com.example.demo.adapters.repository.ProjetoRepository;
import com.example.demo.adapters.repository.UserRepository;
import com.example.demo.adapters.service.ProjetoService;
import com.example.demo.entities.projeto.Projeto;
import com.example.demo.entities.projeto.dto.MetasDTO;
import com.example.demo.entities.projeto.dto.ProjetoRequestDTO;
import com.example.demo.entities.projeto.dto.ProjetoResponseDTO;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.custom.FieldsNotBeNullException;
import com.example.demo.exceptions.custom.ProjetoNotFoundException;
import com.example.demo.infra.security.TokenService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository repository;
    private final UserRepository userRepository;
    private final MetasRepository metasRepository;
    private final Logger logger = LoggerFactory.getLogger(ProjetoServiceImpl.class);
    private final TokenService tokenService;

    public ProjetoServiceImpl(ProjetoRepository repository, UserRepository userRepository, MetasRepository metasRepository, TokenService tokenService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.metasRepository = metasRepository;
        this.tokenService = tokenService;
    }


    @Override
    @Transactional
    public ProjetoResponseDTO createProject(ProjetoRequestDTO data) {
        logger.info("Criando um novo projeto {}", data.nome());
        if(data.nome() == null || data.nome().isEmpty() || data.descricao() == null || data.descricao().isEmpty()) throw new FieldsNotBeNullException("Campo de nome ou/e descricao precisam ser preenchidos");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var jwtToken = (String) authentication.getCredentials();
        String email = tokenService.validateToken(jwtToken);
        User user = (User) userRepository.findByEmail(email);

        Projeto projeto = new Projeto(data, user);
        repository.save(projeto);

        return new ProjetoResponseDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao(), projeto.getDataCriacao(), projeto.getDataAtualizacao(), null);
    }

    @Override
    @Transactional
    public ProjetoResponseDTO updateProject(ProjetoRequestDTO data, Long id) {
        logger.info("Atualizando um projeto");
        var projeto = repository.findById(id).orElseThrow(() -> new ProjetoNotFoundException("Projeto nao encontrado pelo id " + id));
        projeto.updateProjeto(data);
        repository.save(projeto);

        List<MetasDTO> metaDTO = projeto.getMetas().stream()
                .map(meta -> new MetasDTO(meta.getDescricao(), meta.getCompletada() ,meta.getDataLimite()))
                .collect(Collectors.toList());

        return new ProjetoResponseDTO(projeto.getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getDataCriacao(),
                projeto.getDataAtualizacao(),
                metaDTO);
    }

    @Override
    public ProjetoResponseDTO getById(Long id) {
        logger.info("Pegando um projeto pelo id");
        var data = repository.findById(id).orElseThrow(() -> new ProjetoNotFoundException("Projeto não encontrado"));

        List<MetasDTO> metaDTO = data.getMetas().stream()
                .map(meta -> new MetasDTO(meta.getDescricao(), meta.getCompletada() ,meta.getDataLimite()))
                .collect(Collectors.toList());

        return new ProjetoResponseDTO(
                data.getId(),
                data.getNome(),
                data.getDescricao(),
                data.getDataCriacao(),
                data.getDataCriacao(),
                metaDTO
        );
    }

    @Override
    public void deleteProjectById(Long id) {
        logger.info("Deletando projeto pelo id");
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {throw new ProjetoNotFoundException("Projeto nao pode ser encontrado pelo id fornecido");});
    }
}
