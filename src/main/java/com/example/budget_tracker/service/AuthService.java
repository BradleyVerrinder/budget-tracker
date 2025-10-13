package com.example.budget_tracker.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.budget_tracker.dto.LoginRequest;
import com.example.budget_tracker.dto.UserResponse;
import com.example.budget_tracker.model.User;
import com.example.budget_tracker.repository.UserRepository;
import com.example.budget_tracker.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse register(String username, String rawPassword) {
        // make sure to check uniqueness etc.
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user = userRepository.save(user);
        return new UserResponse(user.getId(), user.getUsername());
    }

    // authenticate and return token + user (controller will wrap token response)
    public UserResponse loginAndVerify(LoginRequest request) {
        // authenticate via AuthenticationManager so Spring checks password etc.
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        // load user entity (to get id)
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserResponse(user.getId(), user.getUsername());
    }

    public String generateTokenFor(UserResponse userResponse) {
        // load User entity for id/username (or pass user entity directly)
        User user = userRepository.findById(userResponse.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return jwtUtil.generateToken(user);
    }
}
