package com.example.budget_tracker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.budget_tracker.dto.RegisterRequest;
import com.example.budget_tracker.dto.UserResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Use H2 in-memory DB for CI
class UserEndpointIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // Verifies Spring context boots successfully
    }

    @Test
    void testRegisterUser() {
        // Create the record with constructor
        RegisterRequest requestBody = new RegisterRequest("hajar", "password123");
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        HttpEntity<RegisterRequest> request = new HttpEntity<>(requestBody, headers);
    
        ResponseEntity<UserResponse> postResponse = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/auth/register",
            request,
            UserResponse.class
        );
    
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().username()).isEqualTo("hajar");
    }
}
