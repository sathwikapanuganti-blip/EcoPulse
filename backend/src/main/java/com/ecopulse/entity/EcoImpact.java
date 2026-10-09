package com.ecopulse.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "personal_eco_impact")
public class EcoImpact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "water_saved_litres")
    private Integer waterSavedLitres = 0;

    @Column(name = "food_waste_avoided_kg")
    private Double foodWasteAvoidedKg = 0.0;

    @Column(name = "plastic_avoided_count")
    private Integer plasticAvoidedCount = 0;

    @Column(name = "public_transport_trips")
    private Integer publicTransportTrips = 0;

    @Column(name = "recycling_activities_count")
    private Integer recyclingActivitiesCount = 0;

    @Column(name = "reports_submitted_count")
    private Integer reportsSubmittedCount = 0;

    @Column(name = "eco_impact_score")
    private Integer ecoImpactScore = 10;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public EcoImpact() {}

    public EcoImpact(Long userId) {
        this.userId = userId;
        this.waterSavedLitres = 0;
        this.foodWasteAvoidedKg = 0.0;
        this.plasticAvoidedCount = 0;
        this.publicTransportTrips = 0;
        this.recyclingActivitiesCount = 0;
        this.reportsSubmittedCount = 0;
        this.ecoImpactScore = 10;
        this.updatedAt = LocalDateTime.now();
    }

    public void recalculateScore() {
        // Transparent formula:
        // 1 litre water saved = 0.2 pt
        // 1 kg food waste avoided = 2.0 pt
        // 1 plastic avoided = 0.5 pt
        // 1 public transit trip = 1.5 pt
        // 1 recycling activity = 2.0 pt
        // 1 report submitted = 3.0 pt
        double score = (waterSavedLitres * 0.2) +
                       (foodWasteAvoidedKg * 2.0) +
                       (plasticAvoidedCount * 0.5) +
                       (publicTransportTrips * 1.5) +
                       (recyclingActivitiesCount * 2.0) +
                       (reportsSubmittedCount * 3.0);
        this.ecoImpactScore = (int) Math.round(score);
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getWaterSavedLitres() { return waterSavedLitres; }
    public void setWaterSavedLitres(Integer waterSavedLitres) { this.waterSavedLitres = waterSavedLitres; }

    public Double getFoodWasteAvoidedKg() { return foodWasteAvoidedKg; }
    public void setFoodWasteAvoidedKg(Double foodWasteAvoidedKg) { this.foodWasteAvoidedKg = foodWasteAvoidedKg; }

    public Integer getPlasticAvoidedCount() { return plasticAvoidedCount; }
    public void setPlasticAvoidedCount(Integer plasticAvoidedCount) { this.plasticAvoidedCount = plasticAvoidedCount; }

    public Integer getPublicTransportTrips() { return publicTransportTrips; }
    public void setPublicTransportTrips(Integer publicTransportTrips) { this.publicTransportTrips = publicTransportTrips; }

    public Integer getRecyclingActivitiesCount() { return recyclingActivitiesCount; }
    public void setRecyclingActivitiesCount(Integer recyclingActivitiesCount) { this.recyclingActivitiesCount = recyclingActivitiesCount; }

    public Integer getReportsSubmittedCount() { return reportsSubmittedCount; }
    public void setReportsSubmittedCount(Integer reportsSubmittedCount) { this.reportsSubmittedCount = reportsSubmittedCount; }

    public Integer getEcoImpactScore() { return ecoImpactScore; }
    public void setEcoImpactScore(Integer ecoImpactScore) { this.ecoImpactScore = ecoImpactScore; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
