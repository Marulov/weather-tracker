package com.marulov.weathertracker.controller;

import com.marulov.weathertracker.dto.WeatherDto;
import com.marulov.weathertracker.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDto> getWeatherByCity(@PathVariable("city") String city) {
        return ResponseEntity.ok(weatherService.getWeatherByCity(city));
    }
}
