package com.marulov.weathertracker.dto;

public record WeatherDto(
        String city,
        String country,
        Integer temperature) {
}