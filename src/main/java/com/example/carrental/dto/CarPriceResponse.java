package com.example.carrental.dto;

import java.math.BigDecimal;

public record CarPriceResponse(
        Long carId,
        BigDecimal dailyPriceKzt,
        BigDecimal dailyPriceUsd,
        boolean exchangeRateAvailable
) {
}