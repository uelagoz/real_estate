package com.example.realtor.dto;

import com.example.realtor.enums.ListingType;
import com.example.realtor.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record PropertyCreateDTO(
        @NotNull PropertyType propertyType,
        @NotNull ListingType listingType,
        MultipartFile propertyImage,
        @NotBlank String propertyTitle,
        Integer numberRooms,
        Integer numberFloors,
        Integer floorNumber,
        @NotNull Double metersPerSquare,
        @NotNull Double price,
        @NotBlank String city,
        @NotBlank String district,
        Long listedVia
) {
}
