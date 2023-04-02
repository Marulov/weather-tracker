package com.marulov.weathertracker.dto;

import com.marulov.weathertracker.model.Weather;

import java.time.LocalDateTime;

public record WeatherDto(
        String city,
        String country,
        Integer temperature,
        LocalDateTime updatedTime) {

    public static WeatherDto convert(Weather from) {
        return new WeatherDto(from.getCity(), from.getCountry(), from.getTemperature(), from.getUpdatedTime());
    }
}