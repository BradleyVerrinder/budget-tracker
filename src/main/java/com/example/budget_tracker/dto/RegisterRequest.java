package com.example.budget_tracker.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

    
public record RegisterRequest(
        @NotBlank(message = "username required") @Size(min = 3, max = 50) String username,
        @NotBlank(message = "password required") @Size(min = 8, message = "password must be 8+ chars") String password
    ){}
