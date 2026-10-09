package com.ecopulse.repository;

import com.ecopulse.entity.EcoTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcoTipRepository extends JpaRepository<EcoTip, Long> {
    List<EcoTip> findByCategoryIgnoreCase(String category);
    List<EcoTip> findByTargetGroupIgnoreCase(String targetGroup);
    List<EcoTip> findByLanguageCodeIgnoreCase(String languageCode);
}
