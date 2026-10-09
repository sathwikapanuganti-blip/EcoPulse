package com.ecopulse.service;

import com.ecopulse.dto.CommunityDto;
import com.ecopulse.entity.Community;
import com.ecopulse.repository.CommunityRepository;
import com.ecopulse.repository.EnvironmentalReportRepository;
import com.ecopulse.repository.WaterWasteReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final EnvironmentalReportRepository reportRepository;
    private final WaterWasteReportRepository waterWasteRepo;

    public CommunityService(CommunityRepository communityRepository,
                            EnvironmentalReportRepository reportRepository,
                            WaterWasteReportRepository waterWasteRepo) {
        this.communityRepository = communityRepository;
        this.reportRepository = reportRepository;
        this.waterWasteRepo = waterWasteRepo;
    }

    public List<CommunityDto> getAllCommunities() {
        return communityRepository.findAll().stream().map(this::enrichDto).collect(Collectors.toList());
    }

    public CommunityDto getCommunityDashboard(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseGet(() -> {
                    // Default to first available community or seed fallback
                    List<Community> all = communityRepository.findAll();
                    return all.isEmpty() ? createDefaultCommunity() : all.get(0);
                });
        return enrichDto(community);
    }

    private CommunityDto enrichDto(Community c) {
        CommunityDto dto = new CommunityDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setType(c.getType());
        dto.setCity(c.getCity());
        dto.setArea(c.getArea());
        dto.setTotalMembers(c.getTotalMembers());
        dto.setEcoScore(c.getEcoScore());
        dto.setWaterReductionPct(c.getWaterReductionPct());
        dto.setFoodWasteReducedKg(c.getFoodWasteReducedKg());

        long total = reportRepository.count();
        long resolved = reportRepository.countByStatus("RESOLVED");
        dto.setTotalReports(total);
        dto.setResolvedReports(resolved);
        dto.setResolutionRatePct(total > 0 ? ((double) resolved / total) * 100.0 : 0.0);
        dto.setWaterWasteIncidents(waterWasteRepo.count());
        return dto;
    }

    private Community createDefaultCommunity() {
        Community c = new Community("Green Heights Society", "RESIDENTIAL", "Metropolis", "Central District", 450, 82, 18.2, 130.0);
        return communityRepository.save(c);
    }
}
