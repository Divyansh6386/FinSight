package com.midas.user.service.impl;
import com.midas.user.dto.request.ChangePasswordRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.midas.user.dto.request.UpdateProfileRequest;
import com.midas.user.dto.response.UserProfileResponse;
import com.midas.user.entity.User;
import com.midas.user.repository.UserRepository;
import com.midas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserProfileResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        return UserProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .build();
    }

    @Override
    public UserProfileResponse updateProfile(
            String email,
            UpdateProfileRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        user.setFullName(request.getFullName());

        User updatedUser = userRepository.save(user);

        return UserProfileResponse.builder()
                .id(updatedUser.getId())
                .fullName(updatedUser.getFullName())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole().name())
                .enabled(updatedUser.isEnabled())
                .build();
    }
    @Override
public void changePassword(
        String email,
        ChangePasswordRequest request) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    if (!passwordEncoder.matches(
            request.getCurrentPassword(),
            user.getPassword())) {

        throw new IllegalArgumentException("Current password is incorrect.");
    }

    if (!request.getNewPassword()
            .equals(request.getConfirmPassword())) {

        throw new IllegalArgumentException(
                "New password and confirm password do not match.");
    }

    user.setPassword(
            passwordEncoder.encode(request.getNewPassword()));

    userRepository.save(user);
}
}