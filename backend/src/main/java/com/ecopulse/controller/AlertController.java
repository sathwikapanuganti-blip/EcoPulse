package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.entity.Alert;
import com.ecopulse.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * GET /api/alerts
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Alert>>> getActiveAlerts() {
        return ResponseEntity.ok(ApiResponse.ok(alertService.getActiveAlerts()));
    }

    /**
     * POST /api/alerts
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Alert>> createAlert(@RequestBody Alert alert) {
        return ResponseEntity.ok(ApiResponse.ok(alertService.createAlert(alert)));
    }
}
