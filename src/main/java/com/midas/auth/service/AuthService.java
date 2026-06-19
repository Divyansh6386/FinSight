package com.midas.auth.service;

import com.midas.auth.dto.request.LoginRequest;
import com.midas.auth.dto.request.RegisterRequest;
import com.midas.auth.dto.response.LoginResponse;
import com.midas.auth.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}