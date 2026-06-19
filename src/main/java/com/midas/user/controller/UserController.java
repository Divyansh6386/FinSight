package com.midas.user.controller;

import com.midas.auth.security.CustomUserDetails;
import com.midas.common.response.ApiResponse;
import com.midas.user.dto.request.UpdateProfileRequest;
import com.midas.user.dto.response.UserProfileResponse;
import com.midas.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.midas.user.dto.request.ChangePasswordRequest;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserProfileResponse response =
                userService.getCurrentUser(userDetails.getUsername());

        return ApiResponse.<UserProfileResponse>builder()
                .success(true)
                .message("Profile fetched successfully.")
                .data(response)
                .build();
    }

    @PutMapping("/me")
    public ApiResponse<UserProfileResponse> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest request) {

        UserProfileResponse response =
                userService.updateProfile(userDetails.getUsername(), request);

        return ApiResponse.<UserProfileResponse>builder()
                .success(true)
                .message("Profile updated successfully.")
                .data(response)
                .build();
    }
    @PutMapping("/change-password")
public ApiResponse<Void> changePassword(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody ChangePasswordRequest request) {

    userService.changePassword(
            userDetails.getUsername(),
            request
    );

    return ApiResponse.<Void>builder()
            .success(true)
            .message("Password changed successfully.")
            .build();
}
}