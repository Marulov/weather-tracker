package com.marulov.weathertracker.repository;

import com.marulov.weathertracker.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, String> {

    Optional<Weather> findFirstByCityContainingOrderByUpdatedTimeDesc(String city);
}
