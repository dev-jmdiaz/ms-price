package com.inditex.domain.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
        Integer priceList,
        Integer productId,
        Integer priority,
        Currency currency,
        BigDecimal price,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Brand brand
) {}
