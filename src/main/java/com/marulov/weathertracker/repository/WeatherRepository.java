package com.marulov.weathertracker.repository;

import com.marulov.weathertracker.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, String> {
}
