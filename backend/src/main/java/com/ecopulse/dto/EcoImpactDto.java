package com.ecopulse.dto;

public class EcoImpactDto {
    private Long id;
    private Long userId;
    private Integer waterSavedLitres;
    private Double foodWasteAvoidedKg;
    private Integer plasticAvoidedCount;
    private Integer publicTransportTrips;
    private Integer recyclingActivitiesCount;
    private Integer reportsSubmittedCount;
    private Integer ecoImpactScore;
    private String impactBadge; // "Green Pioneer", "Eco Guardian", "Earth Ally"
    private String summaryStatement;

    public EcoImpactDto() {}

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

    public String getImpactBadge() { return impactBadge; }
    public void setImpactBadge(String impactBadge) { this.impactBadge = impactBadge; }

    public String getSummaryStatement() { return summaryStatement; }
    public void setSummaryStatement(String summaryStatement) { this.summaryStatement = summaryStatement; }
}
