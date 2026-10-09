package com.ecopulse;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.AuthRequest;
import com.ecopulse.dto.AuthResponse;
import com.ecopulse.entity.User;
import com.ecopulse.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Ensure clean state for test users
        userRepository.findByUsername("testcitizen").ifPresent(userRepository::delete);
    }

    @Test
    void testUserRegistrationSuccess() {
        AuthRequest.Register req = new AuthRequest.Register();
        req.setUsername("testcitizen");
        req.setEmail("citizen.test@ecopulse.org");
        req.setPassword("SecurePass123!");
        req.setFullName("Test Green Citizen");
        req.setCommunityId(1L);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/auth/register", req, ApiResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertTrue(userRepository.existsByUsername("testcitizen"));
    }

    @Test
    void testUserRegistrationValidationFailsOnShortUsername() {
        AuthRequest.Register req = new AuthRequest.Register();
        req.setUsername("ab"); // below min 3 chars
        req.setEmail("invalid-email"); // invalid email format
        req.setPassword("12"); // below min password length

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/auth/register", req, ApiResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUserLoginDemoCitizen() {
        AuthRequest.Login req = new AuthRequest.Login();
        req.setUsername("ecouser");
        req.setPassword("ecopulse123");

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/auth/login", req, ApiResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testUserLoginDemoAdmin() {
        AuthRequest.Login req = new AuthRequest.Login();
        req.setUsername("admin");
        req.setPassword("admin123");

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/auth/login", req, ApiResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testUserLoginFailsWithInvalidPassword() {
        AuthRequest.Login req = new AuthRequest.Login();
        req.setUsername("admin");
        req.setPassword("wrongpassword");

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/auth/login", req, ApiResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
