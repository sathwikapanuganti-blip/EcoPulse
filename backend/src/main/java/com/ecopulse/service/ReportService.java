package com.ecopulse.service;

import com.ecopulse.dto.ReportDto;
import com.ecopulse.entity.EnvironmentalReport;
import com.ecopulse.repository.EnvironmentalReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final EnvironmentalReportRepository repository;

    public ReportService(EnvironmentalReportRepository repository) {
        this.repository = repository;
    }

    public ReportDto createReport(ReportDto dto, Long userId) {
        EnvironmentalReport report = new EnvironmentalReport(
                userId,
                dto.getCategory(),
                dto.getLocation(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getSeverity(),
                dto.getDescription(),
                dto.getImageUrl()
        );
        EnvironmentalReport saved = repository.save(report);
        return toDto(saved);
    }

    public List<ReportDto> getAllReports() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReportDto> getMyReports(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReportDto> getNearbyReports(Double lat, Double lon) {
        // Returns recent reports
        return repository.findAllByOrderByCreatedAtDesc()
                .stream().limit(25).map(this::toDto).collect(Collectors.toList());
    }

    public ReportDto updateStatus(Long id, String newStatus, String adminNotes) {
        EnvironmentalReport report = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        report.setStatus(newStatus);
        if (adminNotes != null) {
            report.setAdminNotes(adminNotes);
        }
        EnvironmentalReport updated = repository.save(report);
        return toDto(updated);
    }

    public ReportDto toDto(EnvironmentalReport entity) {
        ReportDto dto = new ReportDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setCategory(entity.getCategory());
        dto.setLocation(entity.getLocation());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setSeverity(entity.getSeverity());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setAdminNotes(entity.getAdminNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
