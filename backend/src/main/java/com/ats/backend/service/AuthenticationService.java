package com.ats.backend.service;

import com.ats.backend.dto.AuthResponse;
import com.ats.backend.dto.LoginRequest;
import com.ats.backend.dto.RegisterRequest;
import com.ats.backend.entity.User;
import com.ats.backend.exception.BadRequestException;
import com.ats.backend.mapper.UserMapper;
import com.ats.backend.repository.UserRepository;
import com.ats.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // 1. Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already in use.");
        }

        // 2. Create the user and hash the password
        var user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // 3. Save to database
        var savedUser = userRepository.save(user);

        // 4. Generate JWT Token
        var jwtToken = jwtService.generateToken(savedUser);

        // 5. Return the token and user details to the frontend
        return AuthResponse.builder()
                .token(jwtToken)
                .user(UserMapper.mapToUserDto(savedUser))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // 1. Spring Security handles the password checking here automatically
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. If we reach this line, the password was correct. Fetch the user.
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        
        // 3. Generate a fresh JWT Token
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .user(UserMapper.mapToUserDto(user))
                .build();
    }
}