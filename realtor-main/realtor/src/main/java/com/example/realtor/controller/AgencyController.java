package com.example.realtor.controller;

import com.example.realtor.dto.AgencyCreateDTO;
import com.example.realtor.dto.AgencyDisplayDTO;
import com.example.realtor.dto.AgencyUpdateDTO;
import com.example.realtor.entity.Agency;
import com.example.realtor.service.AgencyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/agency")
public class AgencyController {

    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @PostMapping
    public ResponseEntity<?> createNewAgency(
            @RequestBody @Valid AgencyCreateDTO agencyCreateDTO,
            HttpSession httpSession
    ) {
        System.out.println("Create new agency called: " + agencyCreateDTO.toString());
        String username = (String) httpSession.getAttribute("username");
        Agency agency = agencyService.createAgency(agencyCreateDTO.agencyName(), agencyCreateDTO.licenseNumber(), agencyCreateDTO.phoneNumber(), agencyCreateDTO.email(), username);
        return new ResponseEntity<>(agency, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateAgency(
            @RequestBody @Valid AgencyUpdateDTO agencyUpdateDTO,
            HttpSession httpSession
    ) {
        String username = (String) httpSession.getAttribute("username");
        Agency agency = agencyService.updateAgency(agencyUpdateDTO.agencyId(), agencyUpdateDTO.agencyName(), agencyUpdateDTO.licenseNumber(), agencyUpdateDTO.phoneNumber(), agencyUpdateDTO.email(), username);
        return ResponseEntity.ok(agency);
    }

    @DeleteMapping("/{agencyId}")
    public ResponseEntity<?> deleteAgency(@PathVariable Long agencyId, HttpSession session) {
        System.out.println("Delete agency: " + agencyId);
        String username = (String) session.getAttribute("username");
        agencyService.deleteAgency(agencyId, username);
        return ResponseEntity.ok("Agency successfully deleted");
    }

    @GetMapping
    public List<AgencyDisplayDTO> getAgencyNames(@RequestParam(required = false) Long agencyId, HttpSession httpSession) {
        List<AgencyDisplayDTO> agencies = new ArrayList<>();
        if (agencyId != null) {
            agencies.add(AgencyDisplayDTO.fromAgency(agencyService.getAgencyById(agencyId)));
        } else {
            String username = (String) httpSession.getAttribute("username");
            agencyService.getAgenciesByUsername(username).forEach(agency -> agencies.add(AgencyDisplayDTO.fromAgency(agency)));
        }
        return agencies;
    }

}
