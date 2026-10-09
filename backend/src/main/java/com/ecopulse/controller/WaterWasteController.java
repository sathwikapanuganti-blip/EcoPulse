package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.WaterWasteDto;
import com.ecopulse.service.EcoImpactService;
import com.ecopulse.service.WaterWasteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/water-waste")
public class WaterWasteController {

    private final WaterWasteService waterWasteService;
    private final EcoImpactService ecoImpactService;

    public WaterWasteController(WaterWasteService waterWasteService, EcoImpactService ecoImpactService) {
        this.waterWasteService = waterWasteService;
        this.ecoImpactService = ecoImpactService;
    }

    /**
     * POST /api/water-waste
     */
    @PostMapping
    public ResponseEntity<ApiResponse<WaterWasteDto>> reportWaterWaste(
            @Valid @RequestBody WaterWasteDto dto,
            @RequestHeader(value = "X-User-Id", required = false) Long headerUserId) {
        Long userId = (dto.getUserId() != null) ? dto.getUserId() : (headerUserId != null ? headerUserId : 1L);
        WaterWasteDto created = waterWasteService.createReport(dto, userId);

        try {
            ecoImpactService.logAction(userId, "WATER_SAVED", 30.0);
        } catch (Exception ignored) {}

        return ResponseEntity.ok(ApiResponse.ok("Water waste incident logged successfully!", created));
    }

    /**
     * GET /api/water-waste
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<WaterWasteDto>>> getAllWaterWaste() {
        return ResponseEntity.ok(ApiResponse.ok(waterWasteService.getAllReports()));
    }

    /**
     * GET /api/water-waste/my
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<WaterWasteDto>>> getMyWaterWaste(
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(waterWasteService.getMyReports(userId)));
    }

    /**
     * PUT /api/water-waste/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<WaterWasteDto>> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        WaterWasteDto updated = waterWasteService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.ok("Water waste status updated to: " + status, updated));
    }
}
