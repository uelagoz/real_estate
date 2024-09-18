package com.example.realtor.repository;

import com.example.realtor.entity.Property;
import com.example.realtor.enums.ListingType;
import com.example.realtor.enums.PropertyType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT p FROM Property p WHERE " +
            "(:propertyId IS NULL OR p.propertyId = :propertyId) AND" +
            "(:city IS NULL OR p.city = :city) AND " +
            "(:district IS NULL OR p.district = :district) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:minNumberRooms IS NULL OR p.numberRooms >= :minNumberRooms) AND " +
            "(:maxNumberRooms IS NULL OR p.numberRooms <= :maxNumberRooms) AND " +
            "(:propertyType IS NULL OR p.propertyType = :propertyType) AND " +
            "(:listingType IS NULL OR p.listingType = :listingType) AND " +
            "(:minMetersPerSquare IS NULL OR p.metersPerSquare >= :minMetersPerSquare) AND " +
            "(:maxMetersPerSquare IS NULL OR p.metersPerSquare <= :maxMetersPerSquare) AND " +
            "(:username IS NULL OR p.listedBy.username = :username)")
    List<Property> filterProperties(
            @Param("propertyId") Long propertyId,
            @Param("city") String city,
            @Param("district") String district,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minNumberRooms") Integer minNumberRooms,
            @Param("maxNumberRooms") Integer maxNumberRooms,
            @Param("propertyType") PropertyType propertyType,
            @Param("listingType") ListingType listingType,
            @Param("minMetersPerSquare") Double minMetersPerSquare,
            @Param("maxMetersPerSquare") Double maxMetersPerSquare,
            @Param("username") String username,
            Sort sort
    );

}
