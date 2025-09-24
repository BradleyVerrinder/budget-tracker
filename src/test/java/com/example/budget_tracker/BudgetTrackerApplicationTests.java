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

import com.example.budget_tracker.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Use H2 in-memory database for CI
class UserEndpointIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // Verifies the Spring context boots successfully
    }

    @Test
    void testCreateAndGetUser() {
        // Create user via POST
        User user = new User("Hajar", "hajar@example.com", "password123");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<User> postResponse = restTemplate.postForEntity(
            "http://localhost:" + port + "/users",
            request,
            User.class
        );
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Retrieve users via GET
        ResponseEntity<User[]> getResponse = restTemplate.getForEntity(
            "http://localhost:" + port + "/users",
            User[].class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).hasSize(1);
        assertThat(getResponse.getBody()[0].getEmail()).isEqualTo("hajar@example.com");
    }
}
