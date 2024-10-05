package com.example.demo.infra.usecases;

import com.example.demo.adapters.repository.UserRepository;
import com.example.demo.entities.user.User;
import com.example.demo.entities.user.dto.UserRequestDTO;
import com.example.demo.entities.user.dto.UserResponseDTO;
import com.example.demo.exceptions.custom.EmailAlreadyExistsException;
import com.example.demo.exceptions.custom.PasswordLenghtException;
import com.example.demo.exceptions.custom.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Deve criar um usuario com sucesso")
    void shouldCreateUserSuccessfully() {
        UserRequestDTO request = new UserRequestDTO("Nome", "email@example.com", "senhaValida123");
        when(userRepository.existsByEmail(request.email())).thenReturn(false);

        UserResponseDTO response = userService.createUser(request);

        assertEquals(request.nome(), response.name());
        assertEquals(request.email(), response.email());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lancar uma excessao se o email ja existir")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserRequestDTO request = new UserRequestDTO("Nome", "email@example.com", "senhaValida123");
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(request));
    }

    @Test
    @DisplayName("Deve lancar uma excessao se a senha for muito curta")
    void shouldThrowExceptionWhenPasswordIsTooShort() {
        UserRequestDTO request = new UserRequestDTO("Nome", "email@example.com", "123");

        assertThrows(PasswordLenghtException.class, () -> userService.createUser(request));
    }

    @Test
    @DisplayName("Deve dar um update em um usuario com sucesso")
    void shouldUpdateUserSuccessfully() {
        Long userId = 1L;
        UserRequestDTO request = new UserRequestDTO("Novo Nome", "novoemail@example.com", "senhaValida123");
        User existingUser = new User("Nome", "email@example.com", "senhaValida123");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UserResponseDTO response = userService.updateUser(request, userId);

        assertEquals(request.nome(), response.name());
        assertEquals(request.email(), response.email());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    @DisplayName("Deve lancar uma excessao se o usuario nao for encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;
        UserRequestDTO request = new UserRequestDTO("Novo Nome", "novoemail@example.com", "senhaValida123");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(request, userId));
    }

    @Test
    @DisplayName("Deve pegar um usuario pelo id com sucesso no banco de dados mockado")
    void shouldReturnUserSuccessfully() {
        Long userId = 1L;
        User user = new User("Nome", "email@example.com", "senhaValida123");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.getUserById(userId);

        assertEquals(user.getNome(), response.name());
        assertEquals(user.getEmail(), response.email());
    }


    @Test
    @DisplayName("Deve deletar um usuario com sucesso")
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;
        User user = new User("Nome", "email@example.com", "senhaValida123");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Deve lancar uma excessao se o usuario nao for encontrado")
    void shouldThrowExceptionWhenUserNotFoundForDeletion() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(userId));
    }


}