package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkHealth() {
        Map<String, Object> details = new HashMap<>();
        details.put("status", "UP");
        details.put("service", "EcoPulse Environmental Monitoring & Community Action Platform");
        details.put("timestamp", LocalDateTime.now());
        details.put("version", "1.0.0");

        // Check database connection
        try {
            Integer dbCheck = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            details.put("database", dbCheck != null && dbCheck == 1 ? "CONNECTED" : "UNHEALTHY");
        } catch (Exception e) {
            details.put("database", "DISCONNECTED: " + e.getMessage());
        }

        return ResponseEntity.ok(ApiResponse.ok("EcoPulse backend service is operational", details));
    }
}
