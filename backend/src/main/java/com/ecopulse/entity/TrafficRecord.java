package com.ecopulse.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "traffic_records")
public class TrafficRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name", nullable = false, length = 150)
    private String locationName;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "congestion_level", nullable = false, length = 30)
    private String congestionLevel; // LOW, MODERATE, HIGH, SEVERE

    @Column(name = "traffic_intensity")
    private Integer trafficIntensity = 50; // 0-100

    @Column(name = "pollution_risk", nullable = false, length = 30)
    private String pollutionRisk; // LOW, MODERATE, ELEVATED, HIGH

    @Column(name = "is_real_time")
    private Boolean isRealTime = true;

    @Column(name = "data_source", length = 80)
    private String dataSource = "COMMUNITY_TRAFFIC_ESTIMATE";

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt = LocalDateTime.now();

    public TrafficRecord() {}

    public TrafficRecord(String locationName, Double latitude, Double longitude, String congestionLevel, Integer trafficIntensity, String pollutionRisk, Boolean isRealTime, String dataSource) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.congestionLevel = congestionLevel;
        this.trafficIntensity = trafficIntensity;
        this.pollutionRisk = pollutionRisk;
        this.isRealTime = isRealTime;
        this.dataSource = dataSource;
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

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
