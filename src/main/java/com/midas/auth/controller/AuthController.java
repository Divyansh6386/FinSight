package com.midas.auth.controller;

import com.midas.auth.dto.request.LoginRequest;
import com.midas.auth.dto.request.RegisterRequest;
import com.midas.auth.dto.response.LoginResponse;
import com.midas.auth.dto.response.RegisterResponse;
import com.midas.auth.service.AuthService;
import com.midas.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RegisterResponse>builder()
                        .success(true)
                        .message("User registered successfully.")
                        .data(response)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Login successful.")
                        .data(response)
                        .build()
        );
    }
}