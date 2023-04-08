package com.marulov.weathertracker.controller;

import com.marulov.weathertracker.controller.validation.CityNameConstraint;
import com.marulov.weathertracker.dto.WeatherDto;
import com.marulov.weathertracker.service.WeatherService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/weather")
@Validated
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    @RateLimiter(name = "user-rate-limit")
    public ResponseEntity<WeatherDto> getWeatherByCity(@PathVariable("city") @CityNameConstraint @NotBlank String city) {
        return ResponseEntity.ok(weatherService.getWeatherByCity(city));
    }
}
