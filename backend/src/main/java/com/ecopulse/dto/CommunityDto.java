package com.ecopulse.dto;

public class CommunityDto {
    private Long id;
    private String name;
    private String type;
    private String city;
    private String area;
    private Integer totalMembers;
    private Integer ecoScore;
    private Double waterReductionPct;
    private Double foodWasteReducedKg;
    private Long totalReports;
    private Long resolvedReports;
    private Double resolutionRatePct;
    private Long waterWasteIncidents;

    public CommunityDto() {}

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

    public Long getTotalReports() { return totalReports; }
    public void setTotalReports(Long totalReports) { this.totalReports = totalReports; }

    public Long getResolvedReports() { return resolvedReports; }
    public void setResolvedReports(Long resolvedReports) { this.resolvedReports = resolvedReports; }

    public Double getResolutionRatePct() { return resolutionRatePct; }
    public void setResolutionRatePct(Double resolutionRatePct) { this.resolutionRatePct = resolutionRatePct; }

    public Long getWaterWasteIncidents() { return waterWasteIncidents; }
    public void setWaterWasteIncidents(Long waterWasteIncidents) { this.waterWasteIncidents = waterWasteIncidents; }
}
