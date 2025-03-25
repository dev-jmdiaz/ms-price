package com.inditex.infrastructure.repository;

import com.inditex.domain.model.PriceResponseDTO;
import com.inditex.infrastructure.mapper.PriceMapper;
import com.inditex.infrastructure.repository.entity.PriceEntity;
import com.inditex.infrastructure.repository.jpa.PriceJpaRepository;
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
class PriceRepositoryImplTest {

    @Mock
    private PriceJpaRepository priceJPARepository;
    @Mock
    private PriceMapper mapper;

    @InjectMocks
    private PriceRepositoryImpl priceRepositoryImpl;

    @Test
    void getPreferredPrice_ShouldReturnMappedPrice_WhenPriceExists() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;

        PriceEntity mockEntity = new PriceEntity();
        PriceResponseDTO mockDto = new PriceResponseDTO();

        when(priceJPARepository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate)).thenReturn(Optional.of(mockEntity));
        when(mapper.toDomain(mockEntity)).thenReturn(mockDto);

        Optional<PriceResponseDTO> result = priceRepositoryImpl.getPreferredPrice(applicationDate, productId, brandId);

        assertTrue(result.isPresent());
        assertEquals(mockDto, result.get());
    }

    @Test
    void getPreferredPrice_ShouldReturnEmpty_WhenPriceDoesNotExist() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;

        when(priceJPARepository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate)).thenReturn(Optional.empty());

        Optional<PriceResponseDTO> result = priceRepositoryImpl.getPreferredPrice(applicationDate, productId, brandId);

        assertFalse(result.isPresent());
    }
}
