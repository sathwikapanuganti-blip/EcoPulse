package com.ecopulse.repository;

import com.ecopulse.entity.AirQualityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirQualityRecordRepository extends JpaRepository<AirQualityRecord, Long> {
    Optional<AirQualityRecord> findTopByLocationNameIgnoreCaseOrderByRecordedAtDesc(String locationName);
    List<AirQualityRecord> findTop5ByOrderByRecordedAtDesc();
}
