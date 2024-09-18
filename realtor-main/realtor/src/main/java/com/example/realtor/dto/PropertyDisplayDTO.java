package com.example.realtor.dto;

import com.example.realtor.entity.Property;
import com.example.realtor.enums.ListingType;
import com.example.realtor.enums.PropertyType;

public record PropertyDisplayDTO(
        Long propertyId,
        PropertyType propertyType,
        ListingType listingType,
        String propertyImage,
        String propertyTitle,
        Integer numberRooms,
        Integer numberFloors,
        Integer floorNumber,
        Double metersPerSquare,
        Double price,
        String city,
        String district,
        Long listedVia,
        Long listedBy
) {

    public static PropertyDisplayDTO fromProperty(Property property) {
        return new PropertyDisplayDTO(
                property.getPropertyId(),
                property.getPropertyType(),
                property.getListingType(),
                property.getPropertyImage(),
                property.getPropertyTitle(),
                property.getNumberRooms(),
                property.getNumberFloors(),
                property.getFloorNumber(),
                property.getMetersPerSquare(),
                property.getPrice(),
                property.getCity(),
                property.getDistrict(),
                (property.getListedVia() != null) ? property.getListedVia().getAgencyId() : null,
                property.getListedBy().getAccountId()
        );
    }

}
