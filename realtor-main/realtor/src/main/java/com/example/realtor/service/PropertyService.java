package com.example.realtor.service;

import com.example.realtor.dto.PropertyDisplayDTO;
import com.example.realtor.entity.Account;
import com.example.realtor.entity.Agency;
import com.example.realtor.entity.Property;
import com.example.realtor.enums.ListingType;
import com.example.realtor.enums.PropertyType;
import com.example.realtor.exceptions.EntityNotPresentException;
import com.example.realtor.exceptions.UnauthorizedActionException;
import com.example.realtor.repository.AccountRepository;
import com.example.realtor.repository.AgencyRepository;
import com.example.realtor.repository.PropertyRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyService {

    @Value("${property.images.directory}")
    private String imagesDirectory;

    private final PropertyRepository propertyRepository;
    private final AccountRepository accountRepository;
    private final AgencyRepository agencyRepository;

    public PropertyService(PropertyRepository propertyRepository, AccountRepository accountRepository, AgencyRepository agencyRepository) {
        this.propertyRepository = propertyRepository;
        this.accountRepository = accountRepository;
        this.agencyRepository = agencyRepository;
    }

    private boolean isFileExtensionAccepted(String fileExtension) {
        List<String> acceptedFileExtensions = List.of("png", "jpg", "jpeg");
        return acceptedFileExtensions.stream().anyMatch(ext -> ext.equalsIgnoreCase(fileExtension));
    }

    private String savePropertyImage(MultipartFile propertyImage) throws IOException {
        String fileExtension = FilenameUtils.getExtension(propertyImage.getOriginalFilename());
        if (isFileExtensionAccepted(fileExtension)) {
            String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;

            Path directoryPath = Paths.get(imagesDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path imagePath = directoryPath.resolve(uniqueFileName);
            Files.copy(propertyImage.getInputStream(), imagePath);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/images/")
                    .path(uniqueFileName)
                    .toUriString();
        } else {
            throw new IOException("Invalid image type!");
        }
    }

    public PropertyDisplayDTO createProperty(
            PropertyType propertyType, ListingType listingType,
            MultipartFile propertyImage,
            String propertyTitle,
            String city, String district,
            Double price, Double metersPerSquare,
            Integer numberRooms, Integer numberFloors, Integer floorNumber,
            Long listedVia, String listedBy
    ) throws IOException {
        Account account = accountRepository.findByUsername(listedBy).orElseThrow(() -> new UnauthorizedActionException("Anonymous not authorized for this action"));
        Agency agency = null;
        if (listedVia != null) {
            agency = agencyRepository.findByAgencyIdAndCreatedByAccountId(listedVia, account.getAccountId()).orElseThrow(() -> new EntityNotPresentException("Given agency could not be found!"));
        }
        String imageUri = null;
        if (propertyImage != null) {
            imageUri = savePropertyImage(propertyImage);
        }
        Property property = new Property(propertyType, listingType, imageUri, propertyTitle, numberRooms, numberFloors, floorNumber, metersPerSquare, price, city, district, agency, account);
        property = propertyRepository.save(property);
        return PropertyDisplayDTO.fromProperty(property);
    }

    public List<PropertyDisplayDTO> getAllProperties() {
        List<PropertyDisplayDTO> properties = new ArrayList<>();
        propertyRepository.findAll().forEach(property -> properties.add(PropertyDisplayDTO.fromProperty(property)));
        return properties;
    }

    public List<PropertyDisplayDTO> getAllPropertiesByFilter(
            Long propertyId,
            String city,
            String district,
            Double minPrice,
            Double maxPrice,
            Double minMetersPerSquare,
            Double maxMetersPerSquare,
            PropertyType propertyType,
            ListingType listingType,
            Integer minNumberRooms,
            Integer maxNumberRooms,
            Sort.Direction priceOrdering,
            String username
    ) {
        Sort.Direction sortDirection = priceOrdering != null ? priceOrdering : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, "price");
        List<Property> properties = propertyRepository.filterProperties(propertyId,
                city, district, minPrice, maxPrice, minNumberRooms, maxNumberRooms,
                (propertyType == PropertyType.ANY) ? null : propertyType,
                (listingType == ListingType.ANY) ? null : listingType,
                minMetersPerSquare, maxMetersPerSquare, username, sort
        );
        List<PropertyDisplayDTO> resultList = new ArrayList<>();
        properties.forEach(property -> resultList.add(PropertyDisplayDTO.fromProperty(property)));
        return resultList;
    }

}
