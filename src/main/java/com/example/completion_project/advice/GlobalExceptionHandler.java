package com.example.completion_project.advice;

import com.example.completion_project.exception.*;
import com.example.completion_project.mapper.MapToAPIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
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

    @ExceptionHandler(BadCredentialsExceptionCustom.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsExceptionCustom e) {
        Map<String, Object> res = new HashMap<>();
        res.put("Lỗi đăng nhập", e.getMessage());
        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(null, res, 401, "Lỗi đăng nhập không thành công"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccessDeniedExceptionCustom.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedExceptionCustom e) {
        Map<String, Object> res = new HashMap<>();
        res.put("Lỗi phân quyền", e.getMessage());

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(null, res, 403, "Truy cập bị từ chối"),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {

        Map<String, Object> errors = new HashMap<>();
        e.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(null, errors, 400, "Lấy dữ liệu không thành công"),
                HttpStatus.BAD_REQUEST
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
        Map<String, Object> res = new HashMap<>();
        res.put("Lỗi không tìm thấy", e.getMessage());

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(null, res, 404, "Không tìm thấy tài nguyên"),
                HttpStatus.NOT_FOUND
        );
    }
}
