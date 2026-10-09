package com.ecopulse.repository;

import com.ecopulse.entity.EcoImpact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcoImpactRepository extends JpaRepository<EcoImpact, Long> {
    Optional<EcoImpact> findByUserId(Long userId);
}
