package com.inditex.application.caseuse;


import com.inditex.application.dto.PriceResponseDTO;

import java.time.LocalDateTime;

public interface IGetPriceUseCase {
    PriceResponseDTO getPreferredPrice(LocalDateTime applicationDate, Integer productId, Integer brandId);
}
