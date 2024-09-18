package com.example.realtor.dto;

public record AccountUpdateDTO(
        String password,
        String firstname,
        String lastname,
        String phoneNumber,
        String email
) {
}
