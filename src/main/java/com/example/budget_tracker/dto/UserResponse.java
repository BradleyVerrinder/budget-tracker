package com.example.budget_tracker.dto;

public record UserResponse(Long id, String username) {
    public static UserResponse fromEntity(com.example.budget_tracker.model.User user){
        return new UserResponse(user.getId(), user.getUsername());
    }
}
