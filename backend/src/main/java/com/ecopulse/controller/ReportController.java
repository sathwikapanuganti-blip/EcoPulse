package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.ReportDto;
import com.ecopulse.service.EcoImpactService;
import com.ecopulse.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final EcoImpactService ecoImpactService;

    public ReportController(ReportService reportService, EcoImpactService ecoImpactService) {
        this.reportService = reportService;
        this.ecoImpactService = ecoImpactService;
    }

    /**
     * POST /api/reports
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ReportDto>> createReport(
            @Valid @RequestBody ReportDto reportDto,
            @RequestHeader(value = "X-User-Id", required = false) Long headerUserId) {
        Long userId = (reportDto.getUserId() != null) ? reportDto.getUserId() : (headerUserId != null ? headerUserId : 1L);
        ReportDto saved = reportService.createReport(reportDto, userId);

        // Update personal eco impact for reporting citizen
        try {
            ecoImpactService.logAction(userId, "REPORT_SUBMITTED", 1.0);
        } catch (Exception ignored) {}

        return ResponseEntity.ok(ApiResponse.ok("Environmental problem reported successfully!", saved));
    }

    /**
     * GET /api/reports/nearby
     */
    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<List<ReportDto>>> getNearbyReports(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon) {
        List<ReportDto> reports = reportService.getNearbyReports(lat, lon);
        return ResponseEntity.ok(ApiResponse.ok(reports));
    }

    /**
     * GET /api/reports/my
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<ReportDto>>> getMyReports(
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        List<ReportDto> reports = reportService.getMyReports(userId);
        return ResponseEntity.ok(ApiResponse.ok(reports));
    }

    /**
     * GET /api/reports (all)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReportDto>>> getAllReports() {
        return ResponseEntity.ok(ApiResponse.ok(reportService.getAllReports()));
    }
}
