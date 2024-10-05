package com.example.demo.exceptions.custom;

public class MetaNotFoundException extends RuntimeException {

    public MetaNotFoundException() {
        super("Meta nao encontrada");
    }

    public MetaNotFoundException(String message) {
        super(message);
    }
}
