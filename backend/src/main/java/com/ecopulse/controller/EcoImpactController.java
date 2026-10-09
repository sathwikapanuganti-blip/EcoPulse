package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.EcoImpactDto;
import com.ecopulse.service.EcoImpactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/eco-impact")
public class EcoImpactController {

    private final EcoImpactService ecoImpactService;

    public EcoImpactController(EcoImpactService ecoImpactService) {
        this.ecoImpactService = ecoImpactService;
    }

    /**
     * GET /api/eco-impact
     */
    @GetMapping
    public ResponseEntity<ApiResponse<EcoImpactDto>> getMyImpact(
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        EcoImpactDto impact = ecoImpactService.getUserImpact(userId);
        return ResponseEntity.ok(ApiResponse.ok(impact));
    }

    /**
     * POST /api/eco-impact
     * Request body: { "actionType": "WATER_SAVED", "amount": 25 }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EcoImpactDto>> logEcoAction(
            @RequestBody Map<String, Object> payload,
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        String actionType = (String) payload.getOrDefault("actionType", "WATER_SAVED");
        double amount = 1.0;
        if (payload.get("amount") != null) {
            amount = Double.parseDouble(payload.get("amount").toString());
        }
        EcoImpactDto updated = ecoImpactService.logAction(userId, actionType, amount);
        return ResponseEntity.ok(ApiResponse.ok("Eco action recorded! Keep up the great work.", updated));
    }
}
