package com.inditex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
@AutoConfigureMockMvc
class InditexApplicationTests {

    private static final int PRODUCT_ID = 35455;
    private static final int BRAND_ID = 1;
    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> priceTestCases() {
        return Stream.of(
                arguments(new BigDecimal("35.50"), "2025-06-14T10:00:00.000"),
                arguments(new BigDecimal("25.45"), "2025-06-14T16:00:00.000"),
                arguments(new BigDecimal("35.50"), "2025-06-14T21:00:00.000"),
                arguments(new BigDecimal("30.50"), "2025-06-15T10:00:00.000"),
                arguments(new BigDecimal("38.95"), "2025-06-16T21:00:00.000")
        );
    }

    @ParameterizedTest
    @MethodSource("priceTestCases")
    @DisplayName("Should return correct price for given application date")
    void shouldReturnCorrectPriceForGivenApplicationDate(BigDecimal expectedPrice, String applicationDate) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(PRODUCT_ID))
                        .param("brandId", String.valueOf(BRAND_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value(expectedPrice.setScale(2, RoundingMode.HALF_UP).doubleValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(BRAND_ID));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when brandId parameter is missing")
    void shouldReturnBadRequestWhenBrandIdIsMissing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("applicationDate", "2025-06-14T10:00:00.000")
                        .param("productId", String.valueOf(PRODUCT_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 404 Not Found when no price is available for the given date and product")
    void shouldReturnNotFoundWhenNoPriceIsAvailable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("applicationDate", "2024-06-14T10:00:00.000")
                        .param("productId", String.valueOf(PRODUCT_ID))
                        .param("brandId", String.valueOf(BRAND_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No price available."));
    }
}
