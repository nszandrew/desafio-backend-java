package com.example.demo.exceptions.custom;

public class PasswordLenghtException extends RuntimeException {

    public PasswordLenghtException() {
        super("Smaller password length");
    }
    public PasswordLenghtException(String message) {
        super(message);
    }
}
