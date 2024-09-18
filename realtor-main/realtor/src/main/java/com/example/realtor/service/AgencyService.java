package com.example.realtor.service;

import com.example.realtor.entity.Account;
import com.example.realtor.entity.Agency;
import com.example.realtor.exceptions.EntityNotPresentException;
import com.example.realtor.exceptions.UnauthorizedActionException;
import com.example.realtor.repository.AccountRepository;
import com.example.realtor.repository.AgencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;
    private final AccountRepository accountRepository;

    public AgencyService(AgencyRepository agencyRepository, AccountRepository accountRepository) {
        this.agencyRepository = agencyRepository;
        this.accountRepository = accountRepository;
    }

    public Agency getAgencyById(Long agencyId) {
        return agencyRepository.findById(agencyId).orElseThrow(() -> new EntityNotPresentException("Given agency could not be found!"));
    }

    public List<Agency> getAllAgencies() {
        return agencyRepository.findAll();
    }

    public Agency createAgency(String agencyName, String licenseNumber, String phoneNumber, String email, String username) {
        Account createdBy = accountRepository.findByUsername(username).orElseThrow(() -> new EntityNotPresentException("Username does not exist!"));
        Agency agency = new Agency(agencyName, licenseNumber, phoneNumber, email, createdBy);
        return agencyRepository.save(agency);
    }

    public Agency updateAgency(Long agencyId, String agencyName, String licenseNumber, String phoneNumber, String email, String username) throws UnauthorizedActionException {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedActionException("Anonymous not authorized for this action"));
        Agency agency = agencyRepository.findById(agencyId).orElseThrow(() -> new EntityNotPresentException("Agency could not be found!"));
        if (!agency.getCreatedBy().equals(account)) {
            throw new UnauthorizedActionException("User is not authorized to update given agency");
        }
        agency.update(agencyName, licenseNumber, phoneNumber, email);
        agencyRepository.save(agency);
        return agency;
    }

    public void deleteAgency(Long agencyId, String username) {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedActionException("Anonymous not authorized for this action"));
        Agency agency = agencyRepository.findById(agencyId).orElseThrow(() -> new EntityNotPresentException("Agency already deleted"));
        if (!agency.getCreatedBy().equals(account)) {
            throw new UnauthorizedActionException("User not authorized for this action");
        }
        agencyRepository.deleteById(agencyId);
    }

    public List<Agency> getAgenciesByUsername(String username) {
        return agencyRepository.findAllByCreatedByUsername(username);
    }

}
