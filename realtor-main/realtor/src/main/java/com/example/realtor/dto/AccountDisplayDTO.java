package com.example.realtor.dto;

import com.example.realtor.entity.Account;

public record AccountDisplayDTO(
        String username,
        String firstName,
        String lastName,
        String phoneNumber,
        String email
) {

    public static AccountDisplayDTO fromAccount(Account account) {
        return new AccountDisplayDTO(
               account.getUsername(),
               account.getFirstName(),
               account.getLastName(),
               account.getPhoneNumber(),
               account.getEmail()
        );
    }

}
