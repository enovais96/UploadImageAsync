package com.bix.upload.dto;

import com.bix.upload.constant.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}