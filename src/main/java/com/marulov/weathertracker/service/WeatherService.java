package com.marulov.weathertracker.service;

import com.marulov.weathertracker.repository.WeatherRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


}
