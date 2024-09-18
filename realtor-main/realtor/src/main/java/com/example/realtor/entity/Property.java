package com.example.realtor.entity;

import com.example.realtor.enums.ListingType;
import com.example.realtor.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    private String propertyImage;
    private String propertyTitle;
    private Integer numberRooms;
    private Integer numberFloors;
    private Integer floorNumber;
    private Double metersPerSquare;
    private Double price;

    // TODO: Store in a better format
    private String city;
    private String district;

    @ManyToOne
    @JoinColumn(name = "listed_by")
    private Account listedBy;

    @ManyToOne
    @JoinColumn(name = "listed_via")
    private Agency listedVia;

    @Column(nullable = false)
    private LocalDateTime listingDateTime;

    @Column(nullable = false)
    private LocalDateTime modificationDateTime;

    public Property(
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
            Agency listedVia,
            Account listedBy
    ) {
        this.propertyType = propertyType;
        this.listingType = listingType;
        this.propertyImage = propertyImage;
        this.propertyTitle = propertyTitle;
        this.numberRooms = numberRooms;
        this.numberFloors = numberFloors;
        this.floorNumber = floorNumber;
        this.metersPerSquare = metersPerSquare;
        this.price = price;
        this.city = city;
        this.district = district;
        this.listedVia = listedVia;
        this.listedBy = listedBy;
        LocalDateTime now = LocalDateTime.now();
        this.listingDateTime = now;
        this.modificationDateTime = now;
    }

}
