package com.example.budget_tracker.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.budget_tracker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{ // User = my entity, Long = the type of my primary key (ID)
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    }

