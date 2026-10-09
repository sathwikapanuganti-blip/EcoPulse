package com.ecopulse.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class WaterWasteDto {
    private Long id;
    private Long userId;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Waste type is required")
    private String wasteType;

    private String severity;

    @NotBlank(message = "Description is required")
    private String description;

    private String imageUrl;
    private String status;
    private Integer estimatedLitresWastedPerHour;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WaterWasteDto() {}

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
