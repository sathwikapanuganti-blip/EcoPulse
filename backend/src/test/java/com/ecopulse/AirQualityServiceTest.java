package com.ecopulse;

import com.ecopulse.dto.AirQualityDto;
import com.ecopulse.repository.AirQualityRecordRepository;
import com.ecopulse.service.AirQualityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AirQualityServiceTest {

    private AirQualityRecordRepository repository;
    private AirQualityService airQualityService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(AirQualityRecordRepository.class);
        airQualityService = new AirQualityService(repository);
    }

    @Test
    void testGetAirQualityFallbackWhenOffline() {
        // Test fallback handling when external API cannot be reached or for null inputs
        AirQualityDto dto = airQualityService.getAirQuality(28.6139, 77.2090, "Central Delhi");

        assertNotNull(dto);
        assertNotNull(dto.getAqi());
        assertNotNull(dto.getStatusLabel());
        assertNotNull(dto.getHealthRecommendation());
        assertEquals("Central Delhi", dto.getLocationName());
    }

    @Test
    void testAqiCategorization() {
        AirQualityDto dto = airQualityService.getAirQuality(0.0, 0.0, "Sample Zone");
        assertTrue(dto.getAqi() >= 0);
        assertTrue(dto.getStatusLabel().equals("Safe") ||
                   dto.getStatusLabel().equals("Moderate") ||
                   dto.getStatusLabel().equals("Poor") ||
                   dto.getStatusLabel().equals("Very Poor") ||
                   dto.getStatusLabel().equals("Hazardous"));
    }
}
