package com.ecopulse.repository;

import com.ecopulse.entity.TrafficRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrafficRecordRepository extends JpaRepository<TrafficRecord, Long> {
    Optional<TrafficRecord> findTopByLocationNameIgnoreCaseOrderByRecordedAtDesc(String locationName);
}
