package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.ReportDto;
import com.ecopulse.entity.User;
import com.ecopulse.repository.UserRepository;
import com.ecopulse.service.AlertService;
import com.ecopulse.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ReportService reportService;
    private final UserRepository userRepository;
    private final AlertService alertService;
    private final com.ecopulse.repository.AirQualityRecordRepository airQualityRecordRepository;

    public AdminController(ReportService reportService, UserRepository userRepository, AlertService alertService, com.ecopulse.repository.AirQualityRecordRepository airQualityRecordRepository) {
        this.reportService = reportService;
        this.userRepository = userRepository;
        this.alertService = alertService;
        this.airQualityRecordRepository = airQualityRecordRepository;
    }

    /**
     * GET /api/admin/reports - View all reports across city
     */
    @GetMapping("/reports")
    public ResponseEntity<ApiResponse<List<ReportDto>>> getAllReportsAdmin() {
        return ResponseEntity.ok(ApiResponse.ok(reportService.getAllReports()));
    }

    /**
     * PUT /api/admin/reports/{id}/status - Update status: REPORTED, UNDER_REVIEW, IN_PROGRESS, RESOLVED
     */
    @PutMapping("/reports/{id}/status")
    public ResponseEntity<ApiResponse<ReportDto>> updateReportStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String adminNotes) {
        ReportDto updated = reportService.updateStatus(id, status, adminNotes);
        return ResponseEntity.ok(ApiResponse.ok("Report status updated to: " + status, updated));
    }

    /**
     * GET /api/admin/users - Manage users list
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.ok(userRepository.findAll()));
    }

    /**
     * DELETE /api/admin/users/{id} - Delete user
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("User not found with id: " + id));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("User deleted successfully", null));
    }

    /**
     * PUT /api/admin/users/{id}/role - Change user role
     */
    @PutMapping("/users/{id}/role")
    public ResponseEntity<ApiResponse<User>> updateUserRole(@PathVariable Long id, @RequestParam String role) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("User not found"));
        }
        user.setRole(role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase());
        return ResponseEntity.ok(ApiResponse.ok("Role updated", userRepository.save(user)));
    }

    /**
     * GET /api/admin/alerts - View all alerts
     */
    @GetMapping("/alerts")
    public ResponseEntity<ApiResponse<List<com.ecopulse.entity.Alert>>> getAllAlertsAdmin() {
        return ResponseEntity.ok(ApiResponse.ok(alertService.getAllAlerts()));
    }

    /**
     * POST /api/admin/alerts - Create alert
     */
    @PostMapping("/alerts")
    public ResponseEntity<ApiResponse<com.ecopulse.entity.Alert>> createAlertAdmin(@RequestBody com.ecopulse.entity.Alert alert) {
        return ResponseEntity.ok(ApiResponse.ok("Alert broadcasted successfully", alertService.createAlert(alert)));
    }

    /**
     * PUT /api/admin/alerts/{id}/toggle - Toggle alert status
     */
    @PutMapping("/alerts/{id}/toggle")
    public ResponseEntity<ApiResponse<com.ecopulse.entity.Alert>> toggleAlert(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Alert status updated", alertService.toggleAlert(id)));
    }

    /**
     * DELETE /api/admin/alerts/{id} - Delete alert
     */
    @DeleteMapping("/alerts/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return ResponseEntity.ok(ApiResponse.ok("Alert deleted successfully", null));
    }

    /**
     * GET /api/admin/environment - View recorded air quality data
     */
    @GetMapping("/environment")
    public ResponseEntity<ApiResponse<List<com.ecopulse.entity.AirQualityRecord>>> getEnvironmentalDataAdmin() {
        return ResponseEntity.ok(ApiResponse.ok(airQualityRecordRepository.findAll()));
    }

    /**
     * POST /api/admin/environment - Log environmental data snapshot
     */
    @PostMapping("/environment")
    public ResponseEntity<ApiResponse<com.ecopulse.entity.AirQualityRecord>> saveEnvironmentalDataAdmin(
            @RequestBody com.ecopulse.entity.AirQualityRecord record) {
        return ResponseEntity.ok(ApiResponse.ok("Environmental snapshot recorded", airQualityRecordRepository.save(record)));
    }

    /**
     * GET /api/admin/analytics - Overview summary
     */
    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAnalytics() {
        Map<String, Object> stats = new HashMap<>();
        List<ReportDto> reports = reportService.getAllReports();
        long resolvedCount = reports.stream().filter(r -> "RESOLVED".equalsIgnoreCase(r.getStatus())).count();
        long inProgressCount = reports.stream().filter(r -> "IN_PROGRESS".equalsIgnoreCase(r.getStatus())).count();

        stats.put("totalReports", reports.size());
        stats.put("resolvedReports", resolvedCount);
        stats.put("inProgressReports", inProgressCount);
        stats.put("pendingReports", reports.size() - resolvedCount - inProgressCount);
        stats.put("totalUsers", userRepository.count());
        return ResponseEntity.ok(ApiResponse.ok(stats));
    }
}
