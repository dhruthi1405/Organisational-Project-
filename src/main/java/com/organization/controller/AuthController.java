package com.organization.controller;

import com.organization.dto.AuthenticationRequest;
import com.organization.dto.AuthenticationResponse;
import com.organization.dto.RegisterRequest;
import com.organization.entity.User;
import com.organization.enums.Role;
import com.organization.repository.UserRepository;
import com.organization.security.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole((request.getRole() != null) ? request.getRole() : Role.EMPLOYEE);


        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtTokenUtil.generateToken(user.getEmail());

        return new AuthenticationResponse(token);
    }
}
