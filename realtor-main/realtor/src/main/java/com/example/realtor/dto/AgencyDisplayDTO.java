package com.example.realtor.dto;

import com.example.realtor.entity.Agency;

public record AgencyDisplayDTO(
        Long agencyId,
        String agencyName,
        String licenseNumber,
        String phoneNumber,
        String email,
        Long createdBy
) {

    public static AgencyDisplayDTO fromAgency(Agency agency) {
        return new AgencyDisplayDTO(
                agency.getAgencyId(),
                agency.getAgencyName(),
                agency.getLicenseNumber(),
                agency.getPhoneNumber(),
                agency.getEmail(),
                agency.getCreatedBy().getAccountId()
        );
    }

}
