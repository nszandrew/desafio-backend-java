package com.example.demo.exceptions.custom;

public class ProjetoNotFoundException extends RuntimeException {

    public ProjetoNotFoundException() {
        super("Projeto nao encontrado");
    }

    public ProjetoNotFoundException(String message) {
        super(message);
    }
}
