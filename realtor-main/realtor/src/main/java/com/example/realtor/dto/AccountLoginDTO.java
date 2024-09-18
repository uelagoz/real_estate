package com.example.realtor.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountLoginDTO(
        @NotBlank String username,
        @NotBlank String password
) {
}
