package com.example.realtor.controller;

import com.example.realtor.dto.AccountDisplayDTO;
import com.example.realtor.dto.AccountLoginDTO;
import com.example.realtor.dto.AccountUpdateDTO;
import com.example.realtor.entity.Account;
import com.example.realtor.exceptions.EntityNotPresentException;
import com.example.realtor.service.AccountService;
import com.example.realtor.util.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<?> getAccount(@RequestParam(required = false) Long accountId, HttpSession session) {
        if (accountId == null) {
            String username = SessionUtils.getUsernameFromSession(session);
            return ResponseEntity.ok(accountService.getAccountByUsername(username));
        }
        AccountDisplayDTO account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewAccount(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "firstname") String firstname,
            @RequestParam(name = "lastname") String lastname,
            @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(name = "email", required = false) String email
    ) {
        AccountDisplayDTO account = accountService.createAccount(username, password, firstname, lastname, phoneNumber, email);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginToAccount(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        AccountLoginDTO dto = new AccountLoginDTO(username, password);
        accountService.login(dto, request, response, session);
        return ResponseEntity.ok("Logged in successfully");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if (session.getAttribute("username") == null) {
            return ResponseEntity.ok("User already logged out");
        }
        accountService.logout(session, request, response);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AccountUpdateDTO dto, HttpSession session) {
        AccountDisplayDTO account = accountService.update(dto.password(), dto.firstname(), dto.lastname(), dto.phoneNumber(), dto.email(), session);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String username = SessionUtils.getUsernameFromSession(session);
        accountService.deleteAccount(username);
        logout(session, request, response);
        return ResponseEntity.ok("Deleted account successfully");
    }

}
