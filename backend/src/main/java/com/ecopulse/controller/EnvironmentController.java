package com.ecopulse.controller;

import com.ecopulse.dto.AirQualityDto;
import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.EcoScoreDto;
import com.ecopulse.service.AirQualityService;
import com.ecopulse.service.EcoScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environment")
public class EnvironmentController {

    private final AirQualityService airQualityService;
    private final EcoScoreService ecoScoreService;

    public EnvironmentController(AirQualityService airQualityService, EcoScoreService ecoScoreService) {
        this.airQualityService = airQualityService;
        this.ecoScoreService = ecoScoreService;
    }

    /**
     * GET /api/environment/air-quality
     * Optional query params: lat, lon, locationName
     */
    @GetMapping("/air-quality")
    public ResponseEntity<ApiResponse<AirQualityDto>> getAirQuality(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) String locationName) {
        AirQualityDto result = airQualityService.getAirQuality(lat, lon, locationName);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    /**
     * GET /api/environment/nearby
     * Returns nearby cities / areas pollution comparison
     */
    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<List<AirQualityDto>>> getNearbyPollution(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon) {
        List<AirQualityDto> nearby = airQualityService.getNearbyPollutionComparison(lat, lon);
        return ResponseEntity.ok(ApiResponse.ok(nearby));
    }

    /**
     * GET /api/environment/eco-score
     * Overall calculated EcoPulse score (0-100) with transparent factor breakdown
     */
    @GetMapping("/eco-score")
    public ResponseEntity<ApiResponse<EcoScoreDto>> getEcoScore(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) String locationName) {
        EcoScoreDto score = ecoScoreService.calculateEcoScore(lat, lon, locationName);
        return ResponseEntity.ok(ApiResponse.ok(score));
    }
}
