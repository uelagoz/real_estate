package com.example.realtor.repository;

import com.example.realtor.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgencyRepository extends JpaRepository<Agency, Long> {

    List<Agency> findAllByCreatedByUsername(String username);

    Optional<Agency> findByAgencyIdAndCreatedByAccountId(Long agencyId, Long createdBy);

}
