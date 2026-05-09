package com.example.completion_project.advice;

import com.example.completion_project.exception.BadCredentialsExceptionCustom;
import com.example.completion_project.exception.DuplicateResourceException;
import com.example.completion_project.exception.JwtExceptionCustom;
import com.example.completion_project.mapper.MapToAPIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JwtExceptionCustom.class)
    public ResponseEntity<?> handleJwtException(JwtExceptionCustom e) {
        Map<String, Object> res = new HashMap<>();
        res.put("Lỗi Token", e.getMessage());
        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(null, res, 401, "Lỗi xác thực Token"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {

        Map<String, Object> errors = new HashMap<>();
        e.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(null, errors, 400, "Lỗi dữ liệu không hợp lệ"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadCredentialsExceptionCustom.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsExceptionCustom e) {
        Map<String, Object> res = new HashMap<>();
        res.put("Lỗi đăng nhập", e.getMessage());
        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(null, res, 401, "Lỗi đăng nhập không thành công"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateResourceException e) {
        Map<String, Object> res = new HashMap<>();
        res.put("Lỗi trùng lặp", e.getMessage());
        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(null, res, 409, "Lỗi dữ liệu trùng lặp"),
                HttpStatus.CONFLICT
        );
    }
}
