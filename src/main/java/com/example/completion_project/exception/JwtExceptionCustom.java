package com.example.completion_project.exception;

public class JwtExceptionCustom extends RuntimeException {
    public JwtExceptionCustom(String message) {
        super(message);
    }
}
