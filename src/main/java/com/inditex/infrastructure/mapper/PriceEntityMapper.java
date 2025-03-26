package com.inditex.infrastructure.mapper;

import com.inditex.domain.model.Price;
import com.inditex.infrastructure.repository.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {
    Price toDomain(PriceEntity entity);
}
