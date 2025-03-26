package com.inditex.infrastructure.adapter;

import com.inditex.domain.model.Price;
import com.inditex.domain.repository.PriceRepository;
import com.inditex.infrastructure.mapper.PriceEntityMapper;
import com.inditex.infrastructure.repository.jpa.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

    private final PriceJpaRepository priceJPARepository;
    private final PriceEntityMapper mapper;
    @Override
    public Optional<Price> getPreferredPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return priceJPARepository.findTopPrice(productId, brandId, applicationDate)
                .map(mapper::toDomain);
    }
}
