package com.marulov.weathertracker.dto;

import com.marulov.weathertracker.dto.weather.stack.Current;
import com.marulov.weathertracker.dto.weather.stack.Location;
import com.marulov.weathertracker.dto.weather.stack.Request;

public record WeatherResponse(
        Request request,
        Location location,
        Current current) {
}
