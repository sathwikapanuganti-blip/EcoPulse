package com.ecopulse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_waste_reports")
public class FoodWasteReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(nullable = false)
    private String location;

    @NotBlank
    @Column(name = "food_type", nullable = false, length = 100)
    private String foodType; // COOKED_MEALS, VEGETABLES_FRUITS, BAKERY, PACKAGED, EVENT_BUFFET, OTHER

    @NotBlank
    @Column(name = "approx_quantity", nullable = false, length = 100)
    private String approxQuantity; // e.g. "25 meals", "10 kg"

    @Column(name = "quantity_meals")
    private Integer quantityMeals = 0;

    @NotBlank
    @Column(nullable = false)
    private String reason; // OVERCOOKING, EVENT_LEFTOVER, EXPIRY, CANCELLED_ORDER, OTHER

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate = LocalDateTime.now();

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(nullable = false, length = 30)
    private String status = "AVAILABLE_FOR_DONATION"; // AVAILABLE_FOR_DONATION, PICKED_UP, COMPOSTED, DISPOSED

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public FoodWasteReport() {}

    public FoodWasteReport(Long userId, String location, String foodType, String approxQuantity, Integer quantityMeals, String reason, String description, String imageUrl) {
        this.userId = userId;
        this.location = location;
        this.foodType = foodType;
        this.approxQuantity = approxQuantity;
        this.quantityMeals = quantityMeals != null ? quantityMeals : 0;
        this.reason = reason;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = "AVAILABLE_FOR_DONATION";
        this.eventDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
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
