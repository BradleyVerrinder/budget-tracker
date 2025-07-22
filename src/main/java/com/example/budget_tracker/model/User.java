package com.example.budget_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="users") // Avoids issue where user is a reserved keyword in SQL
public class User{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String secondname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // Default constructor
    public User(){

    }

    // Constructor
    public User(String firstname, String secondname, String email, String password){
        this.firstname = firstname;
        this.secondname = secondname;
        this.email = email;
        this.password = password;
    }

    // Getters
    public Long getId(){
        return id;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getSecondname(){
        return secondname;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    // Setters
    public void setId(Long id){
        this.id = id;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setSecondname(String secondname){
        this.secondname = secondname;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }
}