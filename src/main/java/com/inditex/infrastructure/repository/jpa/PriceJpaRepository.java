package com.inditex.infrastructure.repository.jpa;

import com.inditex.infrastructure.repository.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Integer> {
    Optional<PriceEntity> findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Integer productId, Integer brandId, LocalDateTime applicationDate, LocalDateTime applicationDate2);

}
