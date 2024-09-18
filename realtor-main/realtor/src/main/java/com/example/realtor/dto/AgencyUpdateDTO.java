package com.example.realtor.dto;

import jakarta.validation.constraints.NotNull;

public record AgencyUpdateDTO(
        @NotNull Long agencyId,
        String agencyName,
        String licenseNumber,
        String phoneNumber,
        String email
) {
}
