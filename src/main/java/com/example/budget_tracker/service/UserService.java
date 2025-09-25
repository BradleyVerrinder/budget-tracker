package com.example.budget_tracker.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.budget_tracker.dto.RegisterRequest;
import com.example.budget_tracker.model.User;
import com.example.budget_tracker.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Saves user to the database
    public User createUser (User user){
        return userRepository.save(user);
    }

    // Business logic
    @Transactional
    public User registerUser(RegisterRequest request){
        String username = request.username().trim().toLowerCase();

        // Check is username is taken
        if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Usernamee already taken");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(request.password());

        // Create user entity
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        // save user
        return createUser(user);
    }
}
