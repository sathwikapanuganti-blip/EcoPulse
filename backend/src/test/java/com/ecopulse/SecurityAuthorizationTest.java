package com.ecopulse;

import com.ecopulse.dto.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityAuthorizationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testPublicEndpointsAreAccessible() {
        ResponseEntity<ApiResponse> healthResponse = restTemplate.getForEntity("/api/health", ApiResponse.class);
        assertEquals(HttpStatus.OK, healthResponse.getStatusCode());
        assertNotNull(healthResponse.getBody());

        ResponseEntity<ApiResponse> envResponse = restTemplate.getForEntity("/api/environment/air-quality?lat=28.6139&lon=77.2090", ApiResponse.class);
        assertEquals(HttpStatus.OK, envResponse.getStatusCode());

        ResponseEntity<ApiResponse> trafficResponse = restTemplate.getForEntity("/api/traffic?lat=28.6139&lon=77.2090", ApiResponse.class);
        assertEquals(HttpStatus.OK, trafficResponse.getStatusCode());
    }

    @Test
    void testAdminEndpointsDenyAnonymousUser() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/admin/users", String.class);
        // Expect forbidden or unauthorized without credentials
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
