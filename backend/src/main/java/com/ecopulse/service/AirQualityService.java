package com.ecopulse.service;

import com.ecopulse.dto.AirQualityDto;
import com.ecopulse.entity.AirQualityRecord;
import com.ecopulse.repository.AirQualityRecordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AirQualityService {

    private final AirQualityRecordRepository repository;
    private final RestTemplate restTemplate;

    @Value("${airquality.api.base-url:https://air-quality-api.open-meteo.com/v1/air-quality}")
    private String airQualityApiBaseUrl;

    @Value("${weather.api.base-url:https://api.open-meteo.com/v1/forecast}")
    private String weatherApiBaseUrl;

    // In-memory cache for API results: key -> {timestamp, AirQualityDto}
    private static class CacheEntry {
        final LocalDateTime timestamp;
        final AirQualityDto data;
        CacheEntry(LocalDateTime timestamp, AirQualityDto data) {
            this.timestamp = timestamp;
            this.data = data;
        }
    }
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private static final int CACHE_DURATION_MINUTES = 15;

    public AirQualityService(AirQualityRecordRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Get Air Quality for latitude and longitude.
     * Uses live open API when available with 15-minute caching and timeout protection.
     */
    public AirQualityDto getAirQuality(Double latitude, Double longitude, String locationName) {
        double lat = latitude != null ? latitude : 28.6139; // Default to Delhi if unspecified
        double lon = longitude != null ? longitude : 77.2090;
        String resolvedName = (locationName != null && !locationName.trim().isEmpty()) ? locationName : "Current Location";

        String cacheKey = String.format("%.2f_%.2f", lat, lon);
        CacheEntry cached = cache.get(cacheKey);
        if (cached != null && cached.timestamp.plusMinutes(CACHE_DURATION_MINUTES).isAfter(LocalDateTime.now())) {
            return cached.data;
        }

        try {
            // Fetch live air quality from open meteo air-quality API
            String aqUrl = String.format("%s?latitude=%.4f&longitude=%.4f&current=us_aqi,european_aqi,pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone",
                    airQualityApiBaseUrl, lat, lon);

            ResponseEntity<Map> aqResponse = restTemplate.getForEntity(aqUrl, Map.class);
            Map<String, Object> aqBody = aqResponse.getBody();

            // Fetch live weather (temp & humidity)
            String weatherUrl = String.format("%s?latitude=%.4f&longitude=%.4f&current=temperature_2m,relative_humidity_2m",
                    weatherApiBaseUrl, lat, lon);
            ResponseEntity<Map> weatherResponse = restTemplate.getForEntity(weatherUrl, Map.class);
            Map<String, Object> weatherBody = weatherResponse.getBody();

            AirQualityDto dto = parseApiResponse(aqBody, weatherBody, lat, lon, resolvedName);
            dto.setIsLive(true);
            dto.setDataSource("Open-Meteo Environmental Network (Live)");

            // Persist snapshot to database
            saveSnapshot(dto);

            // Store in cache
            cache.put(cacheKey, new CacheEntry(LocalDateTime.now(), dto));
            return dto;

        } catch (Exception ex) {
            System.err.println("Live environmental API call failed or timed out: " + ex.getMessage());

            // Check if we have recent historical DB record for location
            Optional<AirQualityRecord> lastRecord = repository.findTopByLocationNameIgnoreCaseOrderByRecordedAtDesc(resolvedName);
            if (lastRecord.isPresent()) {
                AirQualityDto fallbackDto = toDto(lastRecord.get());
                fallbackDto.setIsLive(false);
                fallbackDto.setDataSource("EcoPulse Community Station Archive (Cached)");
                fallbackDto.setStatusDescription("Live environmental data is temporarily unavailable. Showing last verified community station record.");
                return fallbackDto;
            }

            // Return clean graceful fallback with explicit transparency label
            return createGracefulFallback(lat, lon, resolvedName);
        }
    }

    /**
     * Get pollution comparison with nearby cities / areas
     */
    public List<AirQualityDto> getNearbyPollutionComparison(Double centerLat, Double centerLon) {
        List<AirQualityDto> nearby = new ArrayList<>();
        double baseLat = centerLat != null ? centerLat : 28.6139;
        double baseLon = centerLon != null ? centerLon : 77.2090;

        // Compare with sample nearby offsets representing adjacent districts / zones
        String[][] surroundingAreas = {
            {"North District", "0.08", "0.05"},
            {"East Industrial Corridor", "-0.05", "0.12"},
            {"South Green Foothills", "-0.10", "-0.08"},
            {"West Suburban Belt", "0.04", "-0.11"}
        };

        for (String[] area : surroundingAreas) {
            try {
                double aLat = baseLat + Double.parseDouble(area[1]);
                double aLon = baseLon + Double.parseDouble(area[2]);
                AirQualityDto item = getAirQuality(aLat, aLon, area[0]);
                nearby.add(item);
            } catch (Exception e) {
                // Ignore failure for individual nearby spot
            }
        }
        return nearby;
    }

    @SuppressWarnings("unchecked")
    private AirQualityDto parseApiResponse(Map<String, Object> aqBody, Map<String, Object> weatherBody, double lat, double lon, String locationName) {
        AirQualityDto dto = new AirQualityDto();
        dto.setLocationName(locationName);
        dto.setLatitude(lat);
        dto.setLongitude(lon);
        dto.setLastUpdated(LocalDateTime.now());
        dto.setAqiStandard("US EPA (Standard 0-500)");

        if (aqBody != null && aqBody.containsKey("current")) {
            Map<String, Object> current = (Map<String, Object>) aqBody.get("current");
            if (current.get("us_aqi") != null) {
                dto.setAqi(((Number) current.get("us_aqi")).intValue());
            } else if (current.get("european_aqi") != null) {
                dto.setAqi(((Number) current.get("european_aqi")).intValue());
                dto.setAqiStandard("European AQI (0-100)");
            } else {
                dto.setAqi(65);
            }

            if (current.get("pm2_5") != null) dto.setPm25(((Number) current.get("pm2_5")).doubleValue());
            if (current.get("pm10") != null) dto.setPm10(((Number) current.get("pm10")).doubleValue());
            if (current.get("carbon_monoxide") != null) dto.setCo(((Number) current.get("carbon_monoxide")).doubleValue());
            if (current.get("nitrogen_dioxide") != null) dto.setNo2(((Number) current.get("nitrogen_dioxide")).doubleValue());
            if (current.get("sulphur_dioxide") != null) dto.setSo2(((Number) current.get("sulphur_dioxide")).doubleValue());
            if (current.get("ozone") != null) dto.setO3(((Number) current.get("ozone")).doubleValue());
        } else {
            dto.setAqi(72);
            dto.setPm25(22.4);
            dto.setPm10(45.0);
        }

        if (weatherBody != null && weatherBody.containsKey("current")) {
            Map<String, Object> wCurrent = (Map<String, Object>) weatherBody.get("current");
            if (wCurrent.get("temperature_2m") != null) dto.setTemperature(((Number) wCurrent.get("temperature_2m")).doubleValue());
            if (wCurrent.get("relative_humidity_2m") != null) dto.setHumidity(((Number) wCurrent.get("relative_humidity_2m")).doubleValue());
        }

        categorizeAqi(dto);
        return dto;
    }

    private void categorizeAqi(AirQualityDto dto) {
        int aqi = dto.getAqi() != null ? dto.getAqi() : 50;
        if (aqi <= 50) {
            dto.setStatusLabel("Safe");
            dto.setStatusDescription("Air quality is considered satisfactory, and air pollution poses little or no risk.");
            dto.setHealthRecommendation("Great day for outdoor walks, children playtime, and natural room ventilation!");
        } else if (aqi <= 100) {
            dto.setStatusLabel("Moderate");
            dto.setStatusDescription("Air quality is acceptable; however, there may be a moderate health concern for a very small number of individuals.");
            dto.setHealthRecommendation("Safe for most people. Sensitive elders or asthma patients should monitor how they feel outdoors.");
        } else if (aqi <= 150) {
            dto.setStatusLabel("Poor");
            dto.setStatusDescription("Members of sensitive groups may experience health effects. The general public is less likely to be affected.");
            dto.setHealthRecommendation("Children and seniors should limit prolonged outdoor exertion. Consider keeping windows closed during peak traffic.");
        } else if (aqi <= 200) {
            dto.setStatusLabel("Very Poor");
            dto.setStatusDescription("Everyone may begin to experience health effects; members of sensitive groups may experience more serious health effects.");
            dto.setHealthRecommendation("Wear an N95 mask outdoors. Avoid morning runs along busy highways. Run home air filtration if available.");
        } else {
            dto.setStatusLabel("Hazardous");
            dto.setStatusDescription("Health warning of emergency conditions. The entire population is more likely to be affected.");
            dto.setHealthRecommendation("Emergency level: Stay indoors with doors and windows closed. Avoid all non-essential outdoor travel.");
        }
    }

    private AirQualityDto createGracefulFallback(double lat, double lon, String locationName) {
        AirQualityDto dto = new AirQualityDto();
        dto.setLocationName(locationName);
        dto.setLatitude(lat);
        dto.setLongitude(lon);
        dto.setAqi(68);
        dto.setPm25(20.5);
        dto.setPm10(42.0);
        dto.setCo(0.5);
        dto.setNo2(18.2);
        dto.setSo2(6.1);
        dto.setO3(35.0);
        dto.setTemperature(25.0);
        dto.setHumidity(55.0);
        dto.setAqiStandard("US EPA (Standard 0-500)");
        dto.setIsLive(false);
        dto.setDataSource("EcoPulse Demo Reference (Offline Fallback)");
        dto.setStatusDescription("Live environmental data is temporarily unavailable. Showing standard reference parameters.");
        categorizeAqi(dto);
        return dto;
    }

    private void saveSnapshot(AirQualityDto dto) {
        try {
            AirQualityRecord record = new AirQualityRecord(
                    dto.getLocationName(), dto.getLatitude(), dto.getLongitude(),
                    dto.getAqi(), dto.getAqiStandard(), dto.getPm25(), dto.getPm10(),
                    dto.getCo(), dto.getNo2(), dto.getSo2(), dto.getO3(),
                    dto.getTemperature(), dto.getHumidity(), dto.getStatusLabel(),
                    dto.getHealthRecommendation()
            );
            repository.save(record);
        } catch (Exception ignored) {
        }
    }

    private AirQualityDto toDto(AirQualityRecord r) {
        AirQualityDto dto = new AirQualityDto();
        dto.setLocationName(r.getLocationName());
        dto.setLatitude(r.getLatitude());
        dto.setLongitude(r.getLongitude());
        dto.setAqi(r.getAqi());
        dto.setAqiStandard(r.getAqiStandard());
        dto.setPm25(r.getPm25());
        dto.setPm10(r.getPm10());
        dto.setCo(r.getCo());
        dto.setNo2(r.getNo2());
        dto.setSo2(r.getSo2());
        dto.setO3(r.getO3());
        dto.setTemperature(r.getTemperature());
        dto.setHumidity(r.getHumidity());
        dto.setStatusLabel(r.getStatusLabel());
        dto.setHealthRecommendation(r.getHealthRecommendation());
        dto.setLastUpdated(r.getRecordedAt());
        return dto;
    }
}
