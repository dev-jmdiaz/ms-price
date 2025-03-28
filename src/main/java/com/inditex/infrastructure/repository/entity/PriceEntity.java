package com.inditex.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "PRICES", indexes = @Index(columnList = "productId, brandId, startDate, endDate, priority DESC"))
public class PriceEntity {

    @Id
    private Integer priceList;
    private Integer productId;
    private Integer priority;
    @Enumerated(EnumType.STRING)
    @Column(name = "CURR")
    private Currency currency;
    private BigDecimal price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private BrandEntity brand;
}
