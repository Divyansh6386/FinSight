package com.midas.auth.service.impl;

import com.midas.auth.dto.request.LoginRequest;
import com.midas.auth.dto.request.RegisterRequest;
import com.midas.auth.dto.response.LoginResponse;
import com.midas.auth.dto.response.RegisterResponse;
import com.midas.auth.mapper.ManualUserMapper;
import com.midas.auth.security.CustomUserDetails;
import com.midas.auth.security.JwtService;
import com.midas.auth.service.AuthService;
import com.midas.common.exception.UserAlreadyExistsException;
import com.midas.user.entity.User;
import com.midas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
   private final ManualUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userService.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists.");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userService.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        String token = jwtService.generateToken(new CustomUserDetails(user));

        return LoginResponse.builder()
                .accessToken(token)
                .expiresIn(86400L)
                .build();
    }
}