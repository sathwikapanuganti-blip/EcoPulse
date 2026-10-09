package com.ecopulse.service;

import com.ecopulse.dto.FoodWasteDto;
import com.ecopulse.entity.FoodWasteReport;
import com.ecopulse.repository.FoodWasteReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodWasteService {

    private final FoodWasteReportRepository repository;

    public FoodWasteService(FoodWasteReportRepository repository) {
        this.repository = repository;
    }

    public FoodWasteDto createFoodWasteReport(FoodWasteDto dto, Long userId) {
        FoodWasteReport report = new FoodWasteReport(
                userId,
                dto.getLocation(),
                dto.getFoodType(),
                dto.getApproxQuantity(),
                dto.getQuantityMeals() != null ? dto.getQuantityMeals() : parseQuantityToMeals(dto.getApproxQuantity()),
                dto.getReason(),
                dto.getDescription(),
                dto.getImageUrl()
        );
        FoodWasteReport saved = repository.save(report);
        return toDto(saved);
    }

    public List<FoodWasteDto> getAllReports() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<FoodWasteDto> getMyReports(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public FoodWasteDto updateStatus(Long id, String status) {
        FoodWasteReport report = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food waste report not found: " + id));
        report.setStatus(status);
        return toDto(repository.save(report));
    }

    private int parseQuantityToMeals(String qtyStr) {
        if (qtyStr == null) return 5;
        try {
            String numbers = qtyStr.replaceAll("[^0-9]", "");
            return numbers.isEmpty() ? 10 : Integer.parseInt(numbers);
        } catch (Exception e) {
            return 10;
        }
    }

    public FoodWasteDto toDto(FoodWasteReport entity) {
        FoodWasteDto dto = new FoodWasteDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setLocation(entity.getLocation());
        dto.setFoodType(entity.getFoodType());
        dto.setApproxQuantity(entity.getApproxQuantity());
        dto.setQuantityMeals(entity.getQuantityMeals());
        dto.setReason(entity.getReason());
        dto.setDescription(entity.getDescription());
        dto.setEventDate(entity.getEventDate());
        dto.setImageUrl(entity.getImageUrl());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
