package com.example.carrental.service;

import com.example.carrental.dto.CarPriceResponse;
import com.example.carrental.dto.CarResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class CarPriceService {
    private static final Logger log = LoggerFactory.getLogger(CarPriceService.class);

    private final CarService carService;
    private final RestClient restClient;

    public CarPriceService(
            CarService carService,
            @Value("${exchange-rate.base-url}") String baseUrl,
            @Value("${exchange-rate.connect-timeout}") Duration connectTimeout,
            @Value("${exchange-rate.read-timeout}") Duration readTimeout
    ) {
        this.carService = carService;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }

    public CarPriceResponse getPrice(Long id) {
        CarResponse car = carService.getById(id);
        BigDecimal priceKzt = BigDecimal.valueOf(car.getDailyPrice());

        try {
            RateResponse response = restClient.get()
                    .uri("/v2/rate/KZT/USD")
                    .retrieve()
                    .body(RateResponse.class);

            if (response != null && response.rate() != null && response.rate().signum() > 0) {
                BigDecimal priceUsd = priceKzt.multiply(response.rate())
                        .setScale(2, RoundingMode.HALF_UP);

                return new CarPriceResponse(id, priceKzt, priceUsd, true);
            }
        } catch (RestClientException ex) {
            log.warn("Could not get the KZT/USD exchange rate: {}", ex.getMessage());
        }

        return new CarPriceResponse(id, priceKzt, null, false);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record RateResponse(BigDecimal rate) {

    }
}
