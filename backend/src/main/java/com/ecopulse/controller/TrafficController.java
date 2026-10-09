package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.TrafficDto;
import com.ecopulse.service.TrafficService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

    private final TrafficService trafficService;

    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    /**
     * GET /api/traffic
     */
    @GetMapping
    public ResponseEntity<ApiResponse<TrafficDto>> getTraffic(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) String locationName) {
        TrafficDto data = trafficService.getTrafficData(lat, lon, locationName);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }
}
