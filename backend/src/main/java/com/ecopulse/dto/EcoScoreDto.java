package com.ecopulse.dto;

import java.util.Map;

public class EcoScoreDto {
    private Integer score; // 0 - 100
    private String status; // "Healthy & Thriving", "Moderate Risk", "Needs Attention", "Critical Action Required"
    private String explanation;
    private String formula;
    private Map<String, Integer> factorScores; // Air Quality, Traffic, Water Waste, Food Waste
    private Map<String, String> factorWeights;

    public EcoScoreDto() {}

    public EcoScoreDto(Integer score, String status, String explanation, String formula, Map<String, Integer> factorScores, Map<String, String> factorWeights) {
        this.score = score;
        this.status = status;
        this.explanation = explanation;
        this.formula = formula;
        this.factorScores = factorScores;
        this.factorWeights = factorWeights;
    }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getFormula() { return formula; }
    public void setFormula(String formula) { this.formula = formula; }

    public Map<String, Integer> getFactorScores() { return factorScores; }
    public void setFactorScores(Map<String, Integer> factorScores) { this.factorScores = factorScores; }

    public Map<String, String> getFactorWeights() { return factorWeights; }
    public void setFactorWeights(Map<String, String> factorWeights) { this.factorWeights = factorWeights; }
}
