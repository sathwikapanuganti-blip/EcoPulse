package com.ecopulse.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class FoodWasteDto {
    private Long id;
    private Long userId;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Food type is required")
    private String foodType;

    @NotBlank(message = "Approximate quantity is required")
    private String approxQuantity;

    private Integer quantityMeals;

    @NotBlank(message = "Reason is required")
    private String reason;

    private String description;
    private LocalDateTime eventDate;
    private String imageUrl;
    private String status;
    private LocalDateTime createdAt;

    public FoodWasteDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getFoodType() { return foodType; }
    public void setFoodType(String foodType) { this.foodType = foodType; }

    public String getApproxQuantity() { return approxQuantity; }
    public void setApproxQuantity(String approxQuantity) { this.approxQuantity = approxQuantity; }

    public Integer getQuantityMeals() { return quantityMeals; }
    public void setQuantityMeals(Integer quantityMeals) { this.quantityMeals = quantityMeals; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
