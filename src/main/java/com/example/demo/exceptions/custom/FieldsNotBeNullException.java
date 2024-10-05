package com.example.demo.exceptions.custom;

public class FieldsNotBeNullException extends RuntimeException{

    public FieldsNotBeNullException() {
        super("Campos precisam ser preenchidos!");
    }

    public FieldsNotBeNullException(String message) {
        super(message);
    }
}
