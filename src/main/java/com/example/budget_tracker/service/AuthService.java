package com.example.budget_tracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.budget_tracker.dto.LoginRequest;
import com.example.budget_tracker.dto.UserResponse;
import com.example.budget_tracker.model.User;
import com.example.budget_tracker.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }

        return new UserResponse(user.getId(), user.getUsername());
    }
}
