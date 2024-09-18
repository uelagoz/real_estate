package com.example.realtor.controller;

import com.example.realtor.dto.PropertyDisplayDTO;
import com.example.realtor.enums.ListingType;
import com.example.realtor.enums.PropertyType;
import com.example.realtor.service.PropertyService;
import com.example.realtor.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestParam PropertyType propertyType,
            @RequestParam ListingType listingType,
            @RequestPart(required = false) MultipartFile propertyImage,
            @RequestParam String propertyTitle,
            @RequestParam(required = false) Integer numberRooms,
            @RequestParam(required = false) Integer numberFloors,
            @RequestParam(required = false) Integer floorNumber,
            @RequestParam Double metersPerSquare,
            @RequestParam Double price,
            @RequestParam String city,
            @RequestParam String district,
            @RequestParam(required = false) Long listedVia,
            HttpSession session
    ) throws IOException {
        String listedBy = SessionUtils.getUsernameFromSession(session);
        PropertyDisplayDTO property = propertyService.createProperty(
                propertyType, listingType, propertyImage, propertyTitle, city, district, price, metersPerSquare,
                numberRooms, numberFloors, floorNumber, listedVia, listedBy
        );
        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PropertyDisplayDTO> getProperties(
            @RequestParam(required = false) Long propertyId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minMetersPerSquare,
            @RequestParam(required = false) Double maxMetersPerSquare,
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) ListingType listingType,
            @RequestParam(required = false) Integer minNumberRooms,
            @RequestParam(required = false) Integer maxNumberRooms,
            @RequestParam(required = false) Sort.Direction priceOrdering,
            @RequestParam(required = false) String username
    ) {
        System.out.println("Price ordering: " + priceOrdering);
        return propertyService.getAllPropertiesByFilter(
                propertyId, city, district, minPrice, maxPrice, minMetersPerSquare, maxMetersPerSquare,
                propertyType, listingType, minNumberRooms, maxNumberRooms, priceOrdering, username
        );
    }

}
