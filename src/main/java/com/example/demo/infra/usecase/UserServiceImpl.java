package com.example.demo.infra.usecase;

import com.example.demo.adapters.repository.UserRepository;
import com.example.demo.adapters.service.UserService;
import com.example.demo.domain.User;
import com.example.demo.domain.dto.UserRequestDTO;
import com.example.demo.domain.dto.UserResponseDTO;
import com.example.demo.exceptions.custom.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository repository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO data) {
        logger.info("Adicionando um novo usuario {}", data.nome() );
        User newUser = new User(data);
        repository.save(newUser);
        return new UserResponseDTO(data.nome(), data.email(), LocalDateTime.now());
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO userDTO, Long id) {
        logger.info("Atualizando um usuario {}", userDTO.nome());
        var newUser = repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
        newUser.updateUser(userDTO);
        repository.save(newUser);
        return new UserResponseDTO(userDTO.nome(), userDTO.email(), LocalDateTime.now());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        logger.info("Pegando um usuario pelo id: {}", id);
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
        return new UserResponseDTO(user.getNome(), user.getEmail(), user.getDataCriacao());
    }

    @Override
    public void deleteUserById(Long id) {
        logger.info("Deletando um usuario de id: {}", id);
        repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
        repository.deleteById(id);
    }

}
