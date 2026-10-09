package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.FoodWasteDto;
import com.ecopulse.service.EcoImpactService;
import com.ecopulse.service.FoodWasteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-waste")
public class FoodWasteController {

    private final FoodWasteService foodWasteService;
    private final EcoImpactService ecoImpactService;

    public FoodWasteController(FoodWasteService foodWasteService, EcoImpactService ecoImpactService) {
        this.foodWasteService = foodWasteService;
        this.ecoImpactService = ecoImpactService;
    }

    /**
     * POST /api/food-waste
     */
    @PostMapping
    public ResponseEntity<ApiResponse<FoodWasteDto>> reportFoodWaste(
            @Valid @RequestBody FoodWasteDto dto,
            @RequestHeader(value = "X-User-Id", required = false) Long headerUserId) {
        Long userId = (dto.getUserId() != null) ? dto.getUserId() : (headerUserId != null ? headerUserId : 1L);
        FoodWasteDto created = foodWasteService.createFoodWasteReport(dto, userId);

        // Update personal eco impact (food waste avoided/tracked)
        try {
            double meals = created.getQuantityMeals() != null ? created.getQuantityMeals() * 0.4 : 2.0;
            ecoImpactService.logAction(userId, "FOOD_AVOIDED", meals);
        } catch (Exception ignored) {}

        return ResponseEntity.ok(ApiResponse.ok("Food waste logged / surplus donation notified successfully!", created));
    }

    /**
     * GET /api/food-waste
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<FoodWasteDto>>> getAllFoodWaste() {
        return ResponseEntity.ok(ApiResponse.ok(foodWasteService.getAllReports()));
    }

    /**
     * GET /api/food-waste/my
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<FoodWasteDto>>> getMyFoodWaste(
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(foodWasteService.getMyReports(userId)));
    }
}
