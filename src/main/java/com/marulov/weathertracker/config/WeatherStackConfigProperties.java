package com.marulov.weathertracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherStackConfigProperties {

    public static String ACCESS_KEY;

    @Value("${weather-stack.access-key}")
    public void setAccessKey(String accessKey) {
        WeatherStackConfigProperties.ACCESS_KEY = accessKey;
    }
}
