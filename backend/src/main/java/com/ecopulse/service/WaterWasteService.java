package com.ecopulse.service;

import com.ecopulse.dto.WaterWasteDto;
import com.ecopulse.entity.WaterWasteReport;
import com.ecopulse.repository.WaterWasteReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaterWasteService {

    private final WaterWasteReportRepository repository;

    public WaterWasteService(WaterWasteReportRepository repository) {
        this.repository = repository;
    }

    public WaterWasteDto createReport(WaterWasteDto dto, Long userId) {
        WaterWasteReport report = new WaterWasteReport(
                userId,
                dto.getLocation(),
                dto.getWasteType(),
                dto.getSeverity(),
                dto.getDescription(),
                dto.getImageUrl(),
                dto.getEstimatedLitresWastedPerHour() != null ? dto.getEstimatedLitresWastedPerHour() : 15
        );
        WaterWasteReport saved = repository.save(report);
        return toDto(saved);
    }

    public List<WaterWasteDto> getAllReports() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<WaterWasteDto> getMyReports(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public WaterWasteDto updateStatus(Long id, String status) {
        WaterWasteReport report = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Water waste report not found: " + id));
        report.setStatus(status);
        return toDto(repository.save(report));
    }

    public WaterWasteDto toDto(WaterWasteReport entity) {
        WaterWasteDto dto = new WaterWasteDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setLocation(entity.getLocation());
        dto.setWasteType(entity.getWasteType());
        dto.setSeverity(entity.getSeverity());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setStatus(entity.getStatus());
        dto.setEstimatedLitresWastedPerHour(entity.getEstimatedLitresWastedPerHour());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
