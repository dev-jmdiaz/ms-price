package com.inditex.infrastructure.repository.jpa;

import com.inditex.infrastructure.repository.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Integer> {
    @Query("""
                SELECT p FROM PriceEntity p 
                WHERE p.productId = :productId 
                AND p.brand.id = :brandId 
                AND p.startDate <= :date 
                AND p.endDate >= :date 
                ORDER BY p.priority DESC LIMIT 1
            """)
    Optional<PriceEntity> findTopPrice(@Param("productId") Integer productId,
                             @Param("brandId") Integer brandId,
                             @Param("date") LocalDateTime date);

}
