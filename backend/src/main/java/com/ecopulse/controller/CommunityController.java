package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.CommunityDto;
import com.ecopulse.service.CommunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * GET /api/community/dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<CommunityDto>> getDashboard(
            @RequestParam(required = false, defaultValue = "1") Long communityId) {
        CommunityDto dashboard = communityService.getCommunityDashboard(communityId);
        return ResponseEntity.ok(ApiResponse.ok(dashboard));
    }

    /**
     * GET /api/community
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CommunityDto>>> getAllCommunities() {
        return ResponseEntity.ok(ApiResponse.ok(communityService.getAllCommunities()));
    }
}
