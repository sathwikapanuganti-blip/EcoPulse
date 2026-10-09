package com.ecopulse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "eco_tips")
public class EcoTip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String category; // SAVE_WATER, REDUCE_FOOD_WASTE, REDUCE_AIR_POLLUTION, REDUCE_PLASTIC, ENERGY_SAVING, SUSTAINABLE_TRANSPORT, RECYCLING, COMMUNITY_CLEANLINESS

    @NotBlank
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotBlank
    @Column(name = "easy_action", nullable = false, columnDefinition = "TEXT")
    private String easyAction;

    @Column(name = "target_group", length = 50)
    private String targetGroup = "ALL_AGES"; // KIDS, ELDERS, FAMILIES, ALL_AGES

    @Column(name = "language_code", length = 10)
    private String languageCode = "en";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public EcoTip() {}

    public EcoTip(String category, String title, String description, String easyAction, String targetGroup, String languageCode) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.easyAction = easyAction;
        this.targetGroup = targetGroup != null ? targetGroup : "ALL_AGES";
        this.languageCode = languageCode != null ? languageCode : "en";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEasyAction() { return easyAction; }
    public void setEasyAction(String easyAction) { this.easyAction = easyAction; }

    public String getTargetGroup() { return targetGroup; }
    public void setTargetGroup(String targetGroup) { this.targetGroup = targetGroup; }

    public String getLanguageCode() { return languageCode; }
    public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
