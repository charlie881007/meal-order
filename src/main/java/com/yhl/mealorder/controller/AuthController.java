package com.yhl.mealorder.controller;

import com.yhl.mealorder.DTO.AuthenticationRequest;
import com.yhl.mealorder.DTO.AuthenticationResponse;
import com.yhl.mealorder.exception.InvalidArgumentException;
import com.yhl.mealorder.repository.UserAccountRepository;
import com.yhl.mealorder.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final UserAccountRepository userAccountRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService, UserAccountRepository userAccountRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidArgumentException();
        }

        String jwt = jwtService.generateToken(userAccountRepository.findByUsername(authenticationRequest.getUsername()).getId());

        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt(jwt);

        return response;
    }
}
