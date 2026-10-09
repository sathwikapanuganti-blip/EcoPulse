package com.ecopulse.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "air_quality_records")
public class AirQualityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name", nullable = false, length = 150)
    private String locationName;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Integer aqi;

    @Column(name = "aqi_standard", length = 30)
    private String aqiStandard = "US_EPA";

    @Column(name = "pm25")
    private Double pm25;

    @Column(name = "pm10")
    private Double pm10;

    @Column(name = "co")
    private Double co;

    @Column(name = "no2")
    private Double no2;

    @Column(name = "so2")
    private Double so2;

    @Column(name = "o3")
    private Double o3;

    private Double temperature;
    private Double humidity;

    @Column(name = "status_label", nullable = false, length = 30)
    private String statusLabel; // SAFE, MODERATE, POOR, VERY_POOR, HAZARDOUS

    @Column(name = "health_recommendation", columnDefinition = "TEXT")
    private String healthRecommendation;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt = LocalDateTime.now();

    public AirQualityRecord() {}

    public AirQualityRecord(String locationName, Double latitude, Double longitude, Integer aqi, String aqiStandard,
                            Double pm25, Double pm10, Double co, Double no2, Double so2, Double o3,
                            Double temperature, Double humidity, String statusLabel, String healthRecommendation) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.aqi = aqi;
        this.aqiStandard = aqiStandard;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.co = co;
        this.no2 = no2;
        this.so2 = so2;
        this.o3 = o3;
        this.temperature = temperature;
        this.humidity = humidity;
        this.statusLabel = statusLabel;
        this.healthRecommendation = healthRecommendation;
        this.recordedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public String getHealthRecommendation() { return healthRecommendation; }
    public void setHealthRecommendation(String healthRecommendation) { this.healthRecommendation = healthRecommendation; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
