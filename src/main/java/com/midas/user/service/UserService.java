package com.midas.user.service;

import com.midas.user.dto.request.ChangePasswordRequest;
import com.midas.user.dto.request.UpdateProfileRequest;
import com.midas.user.dto.response.UserProfileResponse;
import com.midas.user.entity.User;
import com.midas.user.dto.request.ChangePasswordRequest;

import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    UserProfileResponse getCurrentUser(String email);
     UserProfileResponse updateProfile(
            String email,
            UpdateProfileRequest request);
    void changePassword(
        String email,
        ChangePasswordRequest request);        
}