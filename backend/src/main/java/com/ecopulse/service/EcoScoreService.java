package com.ecopulse.service;

import com.ecopulse.dto.AirQualityDto;
import com.ecopulse.dto.EcoScoreDto;
import com.ecopulse.dto.TrafficDto;
import com.ecopulse.repository.FoodWasteReportRepository;
import com.ecopulse.repository.WaterWasteReportRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class EcoScoreService {

    private final AirQualityService airQualityService;
    private final TrafficService trafficService;
    private final WaterWasteReportRepository waterWasteRepo;
    private final FoodWasteReportRepository foodWasteRepo;

    public EcoScoreService(AirQualityService airQualityService,
                           TrafficService trafficService,
                           WaterWasteReportRepository waterWasteRepo,
                           FoodWasteReportRepository foodWasteRepo) {
        this.airQualityService = airQualityService;
        this.trafficService = trafficService;
        this.waterWasteRepo = waterWasteRepo;
        this.foodWasteRepo = foodWasteRepo;
    }

    /**
     * Calculates transparent EcoPulse Score (0-100).
     * Weights:
     * - Air Quality Factor: 35%
     * - Traffic & Mobility Factor: 25%
     * - Water Waste Factor: 20%
     * - Food Waste Factor: 20%
     */
    public EcoScoreDto calculateEcoScore(Double latitude, Double longitude, String locationName) {
        AirQualityDto aq = airQualityService.getAirQuality(latitude, longitude, locationName);
        TrafficDto traffic = trafficService.getTrafficData(latitude, longitude, locationName);

        // 1. Air Quality Factor Score (0 - 100, where 100 is cleanest)
        int aqi = (aq != null && aq.getAqi() != null) ? aq.getAqi() : 50;
        int airQualityFactorScore = Math.max(0, Math.min(100, 100 - (int)(aqi * 0.4)));

        // 2. Traffic Factor Score (0 - 100, where 100 is zero traffic pollution)
        int trafficIntensity = (traffic != null && traffic.getTrafficIntensity() != null) ? traffic.getTrafficIntensity() : 40;
        int trafficFactorScore = Math.max(0, Math.min(100, 100 - trafficIntensity));

        // 3. Water Waste Factor Score (based on active unresolved leaks)
        long unresolvedWaterReports = waterWasteRepo.countByStatus("REPORTED") + waterWasteRepo.countByStatus("IN_PROGRESS");
        int waterWasteFactorScore = Math.max(20, (int) (95 - (unresolvedWaterReports * 7)));

        // 4. Food Waste Factor Score
        long totalMealsWasted = foodWasteRepo.sumTotalMealsReported();
        int foodWasteFactorScore = Math.max(30, (int) (90 - (totalMealsWasted > 100 ? 25 : totalMealsWasted / 4)));

        // Composite Weighted Score
        double compositeScore = (airQualityFactorScore * 0.35) +
                                (trafficFactorScore * 0.25) +
                                (waterWasteFactorScore * 0.20) +
                                (foodWasteFactorScore * 0.20);

        int finalScore = (int) Math.round(compositeScore);

        String status;
        String explanation;
        if (finalScore >= 80) {
            status = "Healthy & Thriving";
            explanation = "Your local ecosystem is in strong health! Air quality is clean, vehicle pollution is subdued, and community wastage is well controlled.";
        } else if (finalScore >= 60) {
            status = "Needs Attention";
            explanation = "Moderate environmental stress detected. Elevated vehicle movement or localized particulate levels require community awareness and mindful conservation.";
        } else if (finalScore >= 40) {
            status = "Poor Quality - Action Needed";
            explanation = "Pollution and resource waste are impacting the local neighborhood. Coordinated water leak fixes and transit carpooling recommended.";
        } else {
            status = "Critical Alert";
            explanation = "High risk environmental conditions. Extreme air pollution or heavy uncontained resource loss reported in this area.";
        }

        Map<String, Integer> factors = new LinkedHashMap<>();
        factors.put("Air Quality", airQualityFactorScore);
        factors.put("Traffic Flow", trafficFactorScore);
        factors.put("Water Waste Control", waterWasteFactorScore);
        factors.put("Food Waste Control", foodWasteFactorScore);

        Map<String, String> weights = new LinkedHashMap<>();
        weights.put("Air Quality", "35%");
        weights.put("Traffic Flow", "25%");
        weights.put("Water Waste Control", "20%");
        weights.put("Food Waste Control", "20%");

        String formula = "EcoPulse Score = (Air Quality × 35%) + (Traffic Flow × 25%) + (Water Waste Control × 20%) + (Food Waste Control × 20%)";

        return new EcoScoreDto(finalScore, status, explanation, formula, factors, weights);
    }
}
