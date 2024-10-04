package com.example.demo.adapters.controller;

import com.example.demo.adapters.service.UserService;
import com.example.demo.domain.User;
import com.example.demo.domain.dto.AuthDTO;
import com.example.demo.domain.dto.UserRequestDTO;
import com.example.demo.domain.dto.UserResponseDTO;
import com.example.demo.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    @Operation(
            description = "Adicionar um novo usuário ao banco de dados. O corpo da requisição deve conter um JSON com os dados necessários para criar um novo usuário, como nome, email e senha.",
            summary = "Cria um novo usuário",
            responses = {
                    @ApiResponse(
                            description = "Usuário criado com sucesso",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
                    ),
                    @ApiResponse(
                            description = "Dados inválidos ou token não autorizado",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Erro de validação nos dados enviados",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/registro")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(
            description = "Fazer login com Email e Senha",
            summary = "Faz login com email e senha",
            responses = {
                    @ApiResponse(
                            description = "Login feito com sucesso com sucesso",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthDTO.class))
                    ),
                    @ApiResponse(
                            description = "Dados inválidos ou token não autorizado",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Erro de validação nos dados enviados",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO auth){
        var userPass = new UsernamePasswordAuthenticationToken(auth.email(), auth.senha());
        var authorized = authenticationManager.authenticate(userPass);

        var token = tokenService.generateToken((User) authorized.getPrincipal());

        return ResponseEntity.ok(token);
    }


    @Operation(
            description = "Pega um usuario no Banco de dados pelo ID",
            summary = "Pega um usuario pelo ID",
            responses = {
                    @ApiResponse(
                            description = "Usuário encontrado com sucesso",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
                    ),
                    @ApiResponse(
                            description = "Dados inválidos ou token não autorizado",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Erro de validação nos dados enviados",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@Parameter(description = "ID do usuario a ser buscado", required = true) @PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }


    @Operation(
            description = "Atualiza as informacoes de um usuario no banco de dados pelo ID",
            summary = "Atualiza usuario pelo ID",
            responses = {
                    @ApiResponse(
                            description = "Usuário atualizado com sucesso",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
                    ),
                    @ApiResponse(
                            description = "Dados inválidos ou token não autorizado",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Erro de validação nos dados enviados",
                            responseCode = "400"
                    )
            }
    )
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody @Valid UserRequestDTO userRequestDTO, @PathVariable Long id) {
        var newUser = userService.updateUser(userRequestDTO, id);
        return ResponseEntity.ok(newUser);
    }

    @Operation(
            description = "Deleta um usuario pelo id",
            summary = "Deleta um usuario",
            responses = {
                    @ApiResponse(
                            description = "Usuário deletado com sucesso",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Dados inválidos ou token não autorizado",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Erro de validação nos dados enviados",
                            responseCode = "400"
                    )
            }
    )
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
