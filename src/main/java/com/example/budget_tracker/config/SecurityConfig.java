package com.example.budget_tracker.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.budget_tracker.security.DatabaseUserDetailsService;
import com.example.budget_tracker.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final DatabaseUserDetailsService uds;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter, DatabaseUserDetailsService uds) {
        this.jwtFilter = jwtFilter;
        this.uds = uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expose AuthenticationManager so we can use it to authenticate credentials in the service
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow static resources (CSS, JS, images) automatically
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                
                // Allow auth endpoints and HTML pages
                .requestMatchers(
                    "/api/auth/register",
                    "/api/auth/login",
                    "/api/auth/me",
                    "/register.html",
                    "/login.html",
                    "/dashboard.html",
                    "/error" // allow Spring Boot error page
                ).permitAll()
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )

             // Add the JWT filter *before* the UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
