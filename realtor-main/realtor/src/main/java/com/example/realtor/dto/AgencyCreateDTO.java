package com.example.realtor.dto;

import jakarta.validation.constraints.NotBlank;

public record AgencyCreateDTO(
        @NotBlank String agencyName,
        @NotBlank String licenseNumber,
        @NotBlank String phoneNumber,
        @NotBlank String email
) {
}
