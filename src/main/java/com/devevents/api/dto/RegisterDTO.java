package com.devevents.api.dto;

import com.devevents.api.enums.UserRole;
public record RegisterDTO(String email, String password, UserRole role) {}