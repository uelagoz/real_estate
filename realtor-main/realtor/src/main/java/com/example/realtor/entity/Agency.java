package com.example.realtor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agencyId;

    @Column(nullable = false)
    private String agencyName;

    @Column(unique = true, nullable = false)
    private String licenseNumber;

    @Column(nullable = false)
    private String phoneNumber;

    private String email;

    private LocalDateTime creationDateTime;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Account createdBy;


    public Agency(String agencyName, String licenseNumber, String phoneNumber, String email, Account createdBy) {
        this.agencyName = agencyName;
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.createdBy = createdBy;
        this.creationDateTime = LocalDateTime.now();
    }

    public void update(String agencyName, String licenseNumber, String phoneNumber, String email) {
        if (agencyName != null) {
            this.agencyName = agencyName;
        }
        if (licenseNumber != null) {
            this.licenseNumber = licenseNumber;
        }
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
        if (email != null) {
            this.email = email;
        }
    }

}
