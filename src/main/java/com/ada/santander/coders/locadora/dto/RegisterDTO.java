package com.ada.santander.coders.locadora.dto;

import com.ada.santander.coders.locadora.entity.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole userRole) {
}
