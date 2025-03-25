package com.inditex.application.service;

import com.inditex.application.port.IGetPriceUseCase;
import com.inditex.domain.exception.NotFoundException;
import com.inditex.domain.model.PriceResponseDTO;
import com.inditex.domain.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GetPriceUseCaseImpl implements IGetPriceUseCase {

    private final PriceRepository priceRepository;

    @Override
    public PriceResponseDTO getPreferredPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return priceRepository.getPreferredPrice(applicationDate, productId, brandId).
                orElseThrow(() -> new NotFoundException("No price available."));
    }
}
