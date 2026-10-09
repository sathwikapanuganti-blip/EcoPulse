package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.entity.EcoTip;
import com.ecopulse.service.EcoTipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eco-tips")
public class EcoTipController {

    private final EcoTipService ecoTipService;

    public EcoTipController(EcoTipService ecoTipService) {
        this.ecoTipService = ecoTipService;
    }

    /**
     * GET /api/eco-tips
     * Optional filter by category, targetGroup (KIDS, ELDERS, FAMILIES, ALL_AGES), and lang (en, hi, te, es, fr, de)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EcoTip>>> getEcoTips(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String targetGroup,
            @RequestParam(required = false, defaultValue = "en") String lang) {
        List<EcoTip> tips = ecoTipService.getTips(category, targetGroup, lang);
        return ResponseEntity.ok(ApiResponse.ok(tips));
    }
}
