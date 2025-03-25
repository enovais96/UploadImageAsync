package com.bix.upload.dto;

import com.bix.upload.constant.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotBlank(message = "'login' is required")
						  @NotNull(message = "'login' is required")
						  String login,
						  @NotBlank(message = "'password' is required")
						  @NotNull(message = "'password' is required")
						  String password,
						  @NotNull(message = "'role' is required")
						  UserRole role,
						  @NotBlank(message = "'email' is required")
						  @NotNull(message = "'email' is required")
						  String email) {
}