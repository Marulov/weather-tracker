package com.marulov.weathertracker.dto;

import com.marulov.weathertracker.model.Weather;

public record WeatherDto(
        String city,
        String country,
        Integer temperature) {
    public static WeatherDto convert(Weather from) {
        return new WeatherDto(from.getCity(), from.getCountry(), from.getTemperature());
    }
}