package com.inditex.infrastructure.repository;

import com.inditex.domain.model.PriceResponseDTO;
import com.inditex.domain.repository.PriceRepository;
import com.inditex.infrastructure.mapper.PriceMapper;
import com.inditex.infrastructure.repository.jpa.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository priceJPARepository;
    private final PriceMapper mapper;

    @Override
    public Optional<PriceResponseDTO> getPreferredPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return priceJPARepository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate
        ).map(mapper::toDomain);
    }
}
