package com.ecopulse.dto;

import java.time.LocalDateTime;

public class TrafficDto {
    private String locationName;
    private Double latitude;
    private Double longitude;
    private String congestionLevel; // LOW, MODERATE, HIGH, SEVERE
    private Integer trafficIntensity; // 0 - 100
    private String pollutionRisk; // LOW, MODERATE, ELEVATED, HIGH
    private Boolean isRealTime;
    private String dataSource;
    private String advice;
    private LocalDateTime recordedAt;

    public TrafficDto() {
        this.recordedAt = LocalDateTime.now();
    }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getCongestionLevel() { return congestionLevel; }
    public void setCongestionLevel(String congestionLevel) { this.congestionLevel = congestionLevel; }

    public Integer getTrafficIntensity() { return trafficIntensity; }
    public void setTrafficIntensity(Integer trafficIntensity) { this.trafficIntensity = trafficIntensity; }

    public String getPollutionRisk() { return pollutionRisk; }
    public void setPollutionRisk(String pollutionRisk) { this.pollutionRisk = pollutionRisk; }

    public Boolean getIsRealTime() { return isRealTime; }
    public void setIsRealTime(Boolean realTime) { isRealTime = realTime; }

    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }

    public String getAdvice() { return advice; }
    public void setAdvice(String advice) { this.advice = advice; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
