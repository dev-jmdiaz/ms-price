package com.inditex.caseuse;

import com.inditex.application.caseuse.GetPriceUseCaseImpl;
import com.inditex.application.dto.PriceResponseDTO;
import com.inditex.application.mapper.PriceDtoMapper;
import com.inditex.domain.exception.NotFoundException;
import com.inditex.domain.model.Brand;
import com.inditex.domain.model.Currency;
import com.inditex.domain.model.Price;
import com.inditex.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPriceUseCaseImplTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private GetPriceUseCaseImpl getPriceUseCaseImpl;

    @Mock
    private PriceDtoMapper mapper;

    @Test
    void getPreferredPrice_ShouldReturnPrice_WhenPriceExists() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;
        var mockDomain = new Price(0, 0, 0, Currency.EUR,
                BigDecimal.ZERO, LocalDateTime.now(), LocalDateTime.now(),
                new Brand(1, "Zara"));
        var mockDto = new PriceResponseDTO();
        when(mapper.toDto(any())).thenReturn(mockDto);
        when(priceRepository.getPreferredPrice(applicationDate, productId, brandId)).thenReturn(Optional.of(mockDomain));
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
