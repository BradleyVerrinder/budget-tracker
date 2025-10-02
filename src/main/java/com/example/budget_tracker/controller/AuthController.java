package com.example.budget_tracker.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.budget_tracker.dto.LoginRequest;
import com.example.budget_tracker.dto.RegisterRequest;
import com.example.budget_tracker.dto.UserResponse;
import com.example.budget_tracker.model.User;
import com.example.budget_tracker.service.AuthService;
import com.example.budget_tracker.service.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }


    // Controller receives request
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        //TODO: process POST request
        try {
            // Controller calls service
            User savedUser = userService.registerUser(request);

            // Map entity to response DTO
            UserResponse response = UserResponse.fromEntity(savedUser);

            // Return safe JSON response
            return ResponseEntity.status(201).body(response);


        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to register user");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        UserResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
