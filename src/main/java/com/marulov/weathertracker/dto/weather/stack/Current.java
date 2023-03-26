package com.marulov.weathertracker.dto.weather.stack;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record Current(
        Integer temperature,
        @JsonProperty("observation_time")
        String observationTime,
        @JsonProperty("weather_code")
        Integer weatherCode,
        @JsonProperty("weather_icons")
        ArrayList<String> weatherIcons,
        @JsonProperty("weather_descriptions")
        ArrayList<String> weatherDescriptions,
        @JsonProperty("wind_speed")
        Integer windSpeed,
        @JsonProperty("wind_degree")
        Integer windDegree,
        @JsonProperty("wind_dir")
        String windDir,
        Integer pressure,
        Integer precip,
        Integer humidity,
        Integer cloudcover,
        Integer feelslike,
        @JsonProperty("uv_index")
        Integer uvIndex,
        Integer visibility,
        @JsonProperty("is_day")
        String isDay) {
}
