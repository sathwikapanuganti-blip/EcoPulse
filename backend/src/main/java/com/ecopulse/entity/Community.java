package com.ecopulse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "communities")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 50)
    private String type = "RESIDENTIAL"; // RESIDENTIAL, COLLEGE, OFFICE, SOCIETY

    @NotBlank
    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 100)
    private String area;

    @Column(name = "total_members")
    private Integer totalMembers = 1;

    @Column(name = "eco_score")
    private Integer ecoScore = 75;

    @Column(name = "water_reduction_pct")
    private Double waterReductionPct = 0.0;

    @Column(name = "food_waste_reduced_kg")
    private Double foodWasteReducedKg = 0.0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Community() {}

    public Community(String name, String type, String city, String area, Integer totalMembers, Integer ecoScore, Double waterReductionPct, Double foodWasteReducedKg) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.area = area;
        this.totalMembers = totalMembers;
        this.ecoScore = ecoScore;
        this.waterReductionPct = waterReductionPct;
        this.foodWasteReducedKg = foodWasteReducedKg;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Integer getTotalMembers() { return totalMembers; }
    public void setTotalMembers(Integer totalMembers) { this.totalMembers = totalMembers; }

    public Integer getEcoScore() { return ecoScore; }
    public void setEcoScore(Integer ecoScore) { this.ecoScore = ecoScore; }

    public Double getWaterReductionPct() { return waterReductionPct; }
    public void setWaterReductionPct(Double waterReductionPct) { this.waterReductionPct = waterReductionPct; }

    public Double getFoodWasteReducedKg() { return foodWasteReducedKg; }
    public void setFoodWasteReducedKg(Double foodWasteReducedKg) { this.foodWasteReducedKg = foodWasteReducedKg; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
