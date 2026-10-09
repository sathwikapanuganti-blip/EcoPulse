package com.ecopulse.service;

import com.ecopulse.dto.EcoImpactDto;
import com.ecopulse.entity.EcoImpact;
import com.ecopulse.repository.EcoImpactRepository;
import org.springframework.stereotype.Service;

@Service
public class EcoImpactService {

    private final EcoImpactRepository repository;

    public EcoImpactService(EcoImpactRepository repository) {
        this.repository = repository;
    }

    public EcoImpactDto getUserImpact(Long userId) {
        Long safeId = userId != null ? userId : 1L;
        EcoImpact impact = repository.findByUserId(safeId)
                .orElseGet(() -> {
                    EcoImpact newImpact = new EcoImpact(safeId);
                    newImpact.setWaterSavedLitres(120);
                    newImpact.setFoodWasteAvoidedKg(8.5);
                    newImpact.setPlasticAvoidedCount(24);
                    newImpact.setPublicTransportTrips(12);
                    newImpact.setRecyclingActivitiesCount(6);
                    newImpact.setReportsSubmittedCount(2);
                    newImpact.recalculateScore();
                    return repository.save(newImpact);
                });
        return toDto(impact);
    }

    public EcoImpactDto logAction(Long userId, String actionType, double amount) {
        Long safeId = userId != null ? userId : 1L;
        EcoImpact impact = repository.findByUserId(safeId)
                .orElseGet(() -> new EcoImpact(safeId));

        switch (actionType.toUpperCase()) {
            case "WATER_SAVED":
                impact.setWaterSavedLitres(impact.getWaterSavedLitres() + (int) amount);
                break;
            case "FOOD_AVOIDED":
                impact.setFoodWasteAvoidedKg(impact.getFoodWasteAvoidedKg() + amount);
                break;
            case "PLASTIC_AVOIDED":
                impact.setPlasticAvoidedCount(impact.getPlasticAvoidedCount() + (int) amount);
                break;
            case "PUBLIC_TRANSPORT":
                impact.setPublicTransportTrips(impact.getPublicTransportTrips() + (int) amount);
                break;
            case "RECYCLING":
                impact.setRecyclingActivitiesCount(impact.getRecyclingActivitiesCount() + (int) amount);
                break;
            case "REPORT_SUBMITTED":
                impact.setReportsSubmittedCount(impact.getReportsSubmittedCount() + 1);
                break;
            default:
                break;
        }

        impact.recalculateScore();
        return toDto(repository.save(impact));
    }

    public EcoImpactDto toDto(EcoImpact entity) {
        EcoImpactDto dto = new EcoImpactDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setWaterSavedLitres(entity.getWaterSavedLitres());
        dto.setFoodWasteAvoidedKg(entity.getFoodWasteAvoidedKg());
        dto.setPlasticAvoidedCount(entity.getPlasticAvoidedCount());
        dto.setPublicTransportTrips(entity.getPublicTransportTrips());
        dto.setRecyclingActivitiesCount(entity.getRecyclingActivitiesCount());
        dto.setReportsSubmittedCount(entity.getReportsSubmittedCount());
        dto.setEcoImpactScore(entity.getEcoImpactScore());

        int score = entity.getEcoImpactScore();
        if (score >= 150) {
            dto.setImpactBadge("🌟 Planet Champion");
        } else if (score >= 75) {
            dto.setImpactBadge("🌿 Eco Guardian");
        } else {
            dto.setImpactBadge("🌱 Green Pioneer");
        }

        dto.setSummaryStatement(String.format("You helped prevent an estimated %d litres of water waste and avoided %.1f kg of food waste!",
                entity.getWaterSavedLitres(), entity.getFoodWasteAvoidedKg()));
        return dto;
    }
}
