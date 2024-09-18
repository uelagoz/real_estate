package com.example.realtor.service;

import com.example.realtor.dto.AccountDisplayDTO;
import com.example.realtor.dto.AccountLoginDTO;
import com.example.realtor.entity.Account;
import com.example.realtor.exceptions.EntityNotPresentException;
import com.example.realtor.exceptions.UnauthorizedActionException;
import com.example.realtor.repository.AccountRepository;
import com.example.realtor.util.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(
            AccountRepository accountRepository,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AccountDisplayDTO getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotPresentException("Given account could not be found!"));
        return AccountDisplayDTO.fromAccount(account);
    }

    public AccountDisplayDTO getAccountByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedActionException("Anonymous not authorized for this action"));
        return AccountDisplayDTO.fromAccount(account);
    }

    public AccountDisplayDTO createAccount(String username, String password, String firstname, String lastname, String phoneNumber, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        Account account = new Account(username, encodedPassword, firstname, lastname, phoneNumber, email);
        account = accountRepository.save(account);
        return AccountDisplayDTO.fromAccount(account);
    }

    public void login(AccountLoginDTO dto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        SessionUtils.saveUsernameToSession(session, dto.username());
    }

    public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        securityContextRepository.saveContext(SecurityContextHolder.createEmptyContext(), request, response);
        session.invalidate();
    }

    public AccountDisplayDTO update(String password, String firstname, String lastname, String phoneNumber, String email, HttpSession session) {
        String username = SessionUtils.getUsernameFromSession(session);
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedActionException("Anonymous not authorized for this action"));
        if (password != null) {
            password = passwordEncoder.encode(password);
        }
        account.update(password, firstname, lastname, phoneNumber, email);
        account = accountRepository.save(account);
        return AccountDisplayDTO.fromAccount(account);
    }

    public void deleteAccount(String username) {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedActionException("Anonymous not authorized for this action"));
        accountRepository.deleteById(account.getAccountId());
    }
}
