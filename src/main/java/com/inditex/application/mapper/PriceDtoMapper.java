package com.inditex.application.mapper;

import com.inditex.application.dto.PriceResponseDTO;
import com.inditex.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceDtoMapper {
    @Mapping(source = "brand.id", target = "brandId")
    PriceResponseDTO toDto(Price price);
}
