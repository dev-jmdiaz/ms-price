package com.inditex.application.service;

import com.inditex.domain.exception.NotFoundException;
import com.inditex.domain.model.PriceResponseDTO;
import com.inditex.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPriceUseCaseImplTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private GetPriceUseCaseImpl getPriceUseCaseImpl;

    @Test
    void getPreferredPrice_ShouldReturnPrice_WhenPriceExists() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;
        PriceResponseDTO mockDto = new PriceResponseDTO();

        when(priceRepository.getPreferredPrice(applicationDate, productId, brandId)).thenReturn(Optional.of(mockDto));

        PriceResponseDTO result = getPriceUseCaseImpl.getPreferredPrice(applicationDate, productId, brandId);

        assertNotNull(result);
        assertEquals(mockDto, result);
    }

    @Test
    void getPreferredPrice_ShouldThrowException_WhenPriceDoesNotExist() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;

        when(priceRepository.getPreferredPrice(applicationDate, productId, brandId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                getPriceUseCaseImpl.getPreferredPrice(applicationDate, productId, brandId)
        );
    }
}
