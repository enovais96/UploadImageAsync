package com.bix.upload.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(@NotBlank(message = "'login' is required")
								@NotNull(message = "'login' is required")
								String login,
								@NotBlank(message = "'password' is required")
								@NotNull(message = "'password' is required")
								String password) {
}
