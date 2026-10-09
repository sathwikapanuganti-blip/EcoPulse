package com.ecopulse.dto;

import java.time.LocalDateTime;

public class AirQualityDto {
    private String locationName;
    private Double latitude;
    private Double longitude;
    private Integer aqi;
    private String aqiStandard; // US EPA standard or European AQI
    private Double pm25;
    private Double pm10;
    private Double co;
    private Double no2;
    private Double so2;
    private Double o3;
    private Double temperature;
    private Double humidity;
    private String statusLabel; // SAFE, MODERATE, POOR, VERY_POOR, HAZARDOUS
    private String statusDescription;
    private String healthRecommendation;
    private Boolean isLive;
    private String dataSource;
    private LocalDateTime lastUpdated;

    public AirQualityDto() {
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and Setters
    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getAqi() { return aqi; }
    public void setAqi(Integer aqi) { this.aqi = aqi; }

    public String getAqiStandard() { return aqiStandard; }
    public void setAqiStandard(String aqiStandard) { this.aqiStandard = aqiStandard; }

    public Double getPm25() { return pm25; }
    public void setPm25(Double pm25) { this.pm25 = pm25; }

    public Double getPm10() { return pm10; }
    public void setPm10(Double pm10) { this.pm10 = pm10; }

    public Double getCo() { return co; }
    public void setCo(Double co) { this.co = co; }

    public Double getNo2() { return no2; }
    public void setNo2(Double no2) { this.no2 = no2; }

    public Double getSo2() { return so2; }
    public void setSo2(Double so2) { this.so2 = so2; }

    public Double getO3() { return o3; }
    public void setO3(Double o3) { this.o3 = o3; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }

    public String getStatusLabel() { return statusLabel; }
    public void setStatusLabel(String statusLabel) { this.statusLabel = statusLabel; }

    public String getStatusDescription() { return statusDescription; }
    public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

    public String getHealthRecommendation() { return healthRecommendation; }
    public void setHealthRecommendation(String healthRecommendation) { this.healthRecommendation = healthRecommendation; }

    public Boolean getIsLive() { return isLive; }
    public void setIsLive(Boolean live) { isLive = live; }

    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
