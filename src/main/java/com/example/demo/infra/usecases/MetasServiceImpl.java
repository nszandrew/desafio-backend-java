package com.example.demo.infra.usecases;

import com.example.demo.adapters.repository.MetasRepository;
import com.example.demo.adapters.repository.ProjetoRepository;
import com.example.demo.adapters.repository.UserRepository;
import com.example.demo.adapters.service.MetasService;
import com.example.demo.entities.projeto.Metas;
import com.example.demo.entities.projeto.dto.MetasDTO;
import com.example.demo.entities.projeto.dto.MetasResponseDTO;
import com.example.demo.entities.projeto.dto.ProjetoResponseDTO;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.custom.FieldsNotBeNullException;
import com.example.demo.exceptions.custom.MetaNotFoundException;
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
public class MetasServiceImpl implements MetasService {

    private final MetasRepository repository;
    private final Logger logger = LoggerFactory.getLogger(MetasServiceImpl.class);
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final ProjetoRepository projetoRepository;

    public MetasServiceImpl(MetasRepository repository, UserRepository userRepository, TokenService tokenService, ProjetoRepository projetoRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.projetoRepository = projetoRepository;
    }


    @Override
    @Transactional
    public MetasResponseDTO createMeta(MetasDTO data, Long id) {
        logger.info("Criando uma nova meta para o projeto");
        if(data.dataLimite() == null || data.descricao() == null || data.descricao().isEmpty()) throw new FieldsNotBeNullException("Campo de data limite ou/e descricao precisam ser preenchidos");
        var projeto = projetoRepository.findById(id).orElseThrow(() -> new ProjetoNotFoundException("Projeto nao encontrado pelo id " + id));
        Metas meta = new Metas(data, projeto);

        List<MetasDTO> metaDTO = projeto.getMetas().stream()
                .map(metas -> new MetasDTO(metas.getDescricao(), metas.getCompletada() ,metas.getDataLimite()))
                .collect(Collectors.toList());

        ProjetoResponseDTO dto = new ProjetoResponseDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao(), projeto.getDataCriacao(), projeto.getDataAtualizacao(), metaDTO);
        repository.save(meta);

        return new MetasResponseDTO(dto, data);
    }

    @Override
    @Transactional
    public MetasResponseDTO updateMeta(MetasDTO data, Long id) {
        logger.info("Atualizando uma meta para um projeto");
        var metas = repository.findById(id).orElseThrow(() -> new MetaNotFoundException("Meta nao pode ser encontrado pelo id: " + id));
        metas.update(data);
        repository.save(metas);

        return new MetasResponseDTO(null, data);
    }

    @Override
    public MetasResponseDTO getById(Long id) {
        logger.info("Pegando uma meta pelo id");
        var data = repository.findById(id).orElseThrow(() -> new ProjetoNotFoundException("Meta não encontrada pelo id: " + id));
        MetasDTO dto = new MetasDTO(data.getDescricao(), data.getCompletada(), data.getDataLimite());

        return new MetasResponseDTO(null, dto);
    }

    @Override
    public void deleteMeta(Long id) {
        logger.info("Deletando uma meta");
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {throw new MetaNotFoundException("Meta nao encontrada pelo id: " + id );});
    }
}
