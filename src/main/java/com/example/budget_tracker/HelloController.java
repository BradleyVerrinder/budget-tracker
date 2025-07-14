package com.example.budget_tracker;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "Spring Boot App works! YAY! :)";
    }
}