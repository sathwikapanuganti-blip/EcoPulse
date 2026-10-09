package com.ecopulse;

import com.ecopulse.dto.AirQualityDto;
import com.ecopulse.dto.EcoScoreDto;
import com.ecopulse.dto.TrafficDto;
import com.ecopulse.repository.FoodWasteReportRepository;
import com.ecopulse.repository.WaterWasteReportRepository;
import com.ecopulse.service.AirQualityService;
import com.ecopulse.service.EcoScoreService;
import com.ecopulse.service.TrafficService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

class EcoScoreServiceTest {

    private AirQualityService airQualityService;
    private TrafficService trafficService;
    private WaterWasteReportRepository waterRepo;
    private FoodWasteReportRepository foodRepo;
    private EcoScoreService ecoScoreService;

    @BeforeEach
    void setUp() {
        airQualityService = Mockito.mock(AirQualityService.class);
        trafficService = Mockito.mock(TrafficService.class);
        waterRepo = Mockito.mock(WaterWasteReportRepository.class);
        foodRepo = Mockito.mock(FoodWasteReportRepository.class);

        ecoScoreService = new EcoScoreService(airQualityService, trafficService, waterRepo, foodRepo);
    }

    @Test
    void testEcoScoreCalculationTransparency() {
        AirQualityDto mockAq = new AirQualityDto();
        mockAq.setAqi(60);
        Mockito.when(airQualityService.getAirQuality(anyDouble(), anyDouble(), any())).thenReturn(mockAq);

        TrafficDto mockTraffic = new TrafficDto();
        mockTraffic.setTrafficIntensity(30);
        Mockito.when(trafficService.getTrafficData(anyDouble(), anyDouble(), any())).thenReturn(mockTraffic);

        Mockito.when(waterRepo.countByStatus("REPORTED")).thenReturn(1L);
        Mockito.when(waterRepo.countByStatus("IN_PROGRESS")).thenReturn(0L);
        Mockito.when(foodRepo.sumTotalMealsReported()).thenReturn(20L);

        EcoScoreDto result = ecoScoreService.calculateEcoScore(12.9716, 77.5946, "Bengaluru");

        assertNotNull(result);
        assertTrue(result.getScore() >= 0 && result.getScore() <= 100);
        assertNotNull(result.getStatus());
        assertNotNull(result.getFormula());
        assertNotNull(result.getFactorScores());
        assertTrue(result.getFactorScores().containsKey("Air Quality"));
        assertTrue(result.getFactorScores().containsKey("Traffic Flow"));
        assertTrue(result.getFactorScores().containsKey("Water Waste Control"));
        assertTrue(result.getFactorScores().containsKey("Food Waste Control"));
    }
}
