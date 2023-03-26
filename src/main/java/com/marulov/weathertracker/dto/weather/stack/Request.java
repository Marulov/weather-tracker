package com.marulov.weathertracker.dto.weather.stack;

public record Request(
        String type,
        String query,
        String language,
        String unit) {
}
