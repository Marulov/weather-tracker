package com.marulov.weathertracker.dto.weather.stack;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Location(
        String name,
        String country,
        @JsonProperty("localtime")
        String localTime,
        String region,
        String lat,
        String lon,
        @JsonProperty("timezone_id")
        String timezoneId,
        @JsonProperty("localtime_epoch")
        Integer localtimeEpoch,
        @JsonProperty("utc_offset")
        String utcOffset) {
}
