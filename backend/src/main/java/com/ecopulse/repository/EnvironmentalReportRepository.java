package com.ecopulse.repository;

import com.ecopulse.entity.EnvironmentalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentalReportRepository extends JpaRepository<EnvironmentalReport, Long> {
    List<EnvironmentalReport> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<EnvironmentalReport> findAllByOrderByCreatedAtDesc();
    List<EnvironmentalReport> findByCategoryIgnoreCaseOrderByCreatedAtDesc(String category);
    List<EnvironmentalReport> findByStatusIgnoreCaseOrderByCreatedAtDesc(String status);
    long countByStatus(String status);
}
