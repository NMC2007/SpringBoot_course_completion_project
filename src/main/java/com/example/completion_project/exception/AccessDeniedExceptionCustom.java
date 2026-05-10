package com.example.completion_project.exception;

public class AccessDeniedExceptionCustom extends RuntimeException {
    public AccessDeniedExceptionCustom(String message) {
        super(message);
    }
}
