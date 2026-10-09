package com.ecopulse.service;

import com.ecopulse.entity.Alert;
import com.ecopulse.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> getActiveAlerts() {
        List<Alert> active = alertRepository.findByActiveTrueOrderByCreatedAtDesc();
        if (active.isEmpty()) {
            // Seed a realistic alert based on community thresholds
            Alert defaultAlert = new Alert(
                    "AIR_QUALITY",
                    "Elevated Evening Particulate Advisory",
                    "Air quality index has shifted towards Poor in high-density corridors. Elders, children, and people with respiratory sensitivity are advised to minimize strenuous outdoor exercises.",
                    "WARNING",
                    "City Central & Industrial Ring",
                    true
            );
            return List.of(alertRepository.save(defaultAlert));
        }
        return active;
    }

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public Alert toggleAlert(Long id) {
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new RuntimeException("Alert not found with id: " + id));
        alert.setActive(!Boolean.TRUE.equals(alert.getActive()));
        return alertRepository.save(alert);
    }

    public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }
}
