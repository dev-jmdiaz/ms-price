package com.inditex.infrastructure.adapter;

import com.inditex.domain.model.Brand;
import com.inditex.domain.model.Currency;
import com.inditex.domain.model.Price;
import com.inditex.infrastructure.mapper.PriceEntityMapper;
import com.inditex.infrastructure.repository.entity.PriceEntity;
import com.inditex.infrastructure.repository.jpa.PriceJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

    @Mock
    private PriceJpaRepository priceJPARepository;
    @Mock
    private PriceEntityMapper mapper;

    @InjectMocks
    private PriceRepositoryAdapter priceRepositoryImpl;

    @Test
    void getPreferredPrice_ShouldReturnMappedPrice_WhenPriceExists() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;

        PriceEntity mockEntity = new PriceEntity();
        Price mockDto = new Price(0, productId, 0, Currency.EUR,
                BigDecimal.ZERO, LocalDateTime.now(), LocalDateTime.now(),
                new Brand(brandId, "Zara"));

        when(priceJPARepository.findTopPrice(
                productId, brandId, applicationDate)).thenReturn(Optional.of(mockEntity));
        when(mapper.toDomain(mockEntity)).thenReturn(mockDto);

        Optional<Price> result = priceRepositoryImpl.getPreferredPrice(applicationDate, productId, brandId);

        assertTrue(result.isPresent());
        assertEquals(mockDto, result.get());
    }

    @Test
    void getPreferredPrice_ShouldReturnEmpty_WhenPriceDoesNotExist() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;

        when(priceJPARepository.findTopPrice(
                productId, brandId, applicationDate)).thenReturn(Optional.empty());

        Optional<Price> result = priceRepositoryImpl.getPreferredPrice(applicationDate, productId, brandId);

        assertFalse(result.isPresent());
    }
}
