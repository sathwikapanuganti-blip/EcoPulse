package com.ecopulse.repository;

import com.ecopulse.entity.WaterWasteReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterWasteReportRepository extends JpaRepository<WaterWasteReport, Long> {
    List<WaterWasteReport> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<WaterWasteReport> findAllByOrderByCreatedAtDesc();
    long countByStatus(String status);
}
