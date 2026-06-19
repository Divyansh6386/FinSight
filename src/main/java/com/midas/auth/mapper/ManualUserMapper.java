package com.midas.auth.mapper;

import com.midas.auth.dto.request.RegisterRequest;
import com.midas.auth.dto.response.RegisterResponse;
import com.midas.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ManualUserMapper {

    public User toEntity(RegisterRequest request) {

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }

    public RegisterResponse toResponse(User user) {

        return RegisterResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}