package com.midas.common.advice;

import com.midas.common.exception.InvalidCredentialsException;
import com.midas.common.exception.ResourceNotFoundException;
import com.midas.common.exception.UserAlreadyExistsException;
import com.midas.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .build());
    }

 @ExceptionHandler(InvalidCredentialsException.class)
public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(
        InvalidCredentialsException ex) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.<Void>builder()
                    .success(false)
                    .message(ex.getMessage())
                    .build());
}
    }
