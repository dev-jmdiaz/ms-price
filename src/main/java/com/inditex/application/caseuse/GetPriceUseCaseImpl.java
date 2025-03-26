package com.inditex.application.caseuse;

import com.inditex.application.dto.PriceResponseDTO;
import com.inditex.application.mapper.PriceDtoMapper;
import com.inditex.domain.exception.NotFoundException;
import com.inditex.domain.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GetPriceUseCaseImpl implements IGetPriceUseCase {

    private final PriceRepository priceRepository;
    private final PriceDtoMapper mapper;
    @Override
    public PriceResponseDTO getPreferredPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return priceRepository.getPreferredPrice(applicationDate, productId, brandId)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("No price available."));
    }
}
