package com.ecopulse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "water_waste_reports")
public class WaterWasteReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(nullable = false)
    private String location;

    @NotBlank
    @Column(name = "waste_type", nullable = false, length = 80)
    private String wasteType; // LEAKING_PIPE, OVERFLOWING_TANK, OPEN_TAP, PIPE_BURST, EXCESSIVE_USAGE, OTHER

    @Column(nullable = false, length = 20)
    private String severity = "MEDIUM"; // LOW, MEDIUM, HIGH, CRITICAL

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(nullable = false, length = 20)
    private String status = "REPORTED"; // REPORTED, UNDER_REVIEW, IN_PROGRESS, RESOLVED

    @Column(name = "estimated_litres_wasted_per_hour")
    private Integer estimatedLitresWastedPerHour = 10;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public WaterWasteReport() {}

    public WaterWasteReport(Long userId, String location, String wasteType, String severity, String description, String imageUrl, Integer estimatedLitresWastedPerHour) {
        this.userId = userId;
        this.location = location;
        this.wasteType = wasteType;
        this.severity = severity != null ? severity : "MEDIUM";
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = "REPORTED";
        this.estimatedLitresWastedPerHour = estimatedLitresWastedPerHour != null ? estimatedLitresWastedPerHour : 10;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getWasteType() { return wasteType; }
    public void setWasteType(String wasteType) { this.wasteType = wasteType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getEstimatedLitresWastedPerHour() { return estimatedLitresWastedPerHour; }
    public void setEstimatedLitresWastedPerHour(Integer estimatedLitresWastedPerHour) { this.estimatedLitresWastedPerHour = estimatedLitresWastedPerHour; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
