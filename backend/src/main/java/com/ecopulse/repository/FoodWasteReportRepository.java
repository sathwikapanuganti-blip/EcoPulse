package com.ecopulse.repository;

import com.ecopulse.entity.FoodWasteReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodWasteReportRepository extends JpaRepository<FoodWasteReport, Long> {
    List<FoodWasteReport> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<FoodWasteReport> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(SUM(f.quantityMeals), 0) FROM FoodWasteReport f")
    Long sumTotalMealsReported();
}
