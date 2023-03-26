package com.marulov.weathertracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marulov.weathertracker.dto.WeatherDto;
import com.marulov.weathertracker.dto.WeatherResponse;
import com.marulov.weathertracker.model.Weather;
import com.marulov.weathertracker.repository.WeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {

    private static final String API_URL = "https://api.weatherstack.com/current?access_key=dfdb902176aed1f14faed3bb938d83de&query=";
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherDto getWeatherByCity(String city) {
        Optional<Weather> weatherOptional = weatherRepository.findFirstByCityContainingOrderByUpdatedTimeDesc(city);

        if (!weatherOptional.isPresent()) {
            return WeatherDto.convert(getWeatherFromWeatherStackApi(city));
        }

        return WeatherDto.convert(weatherOptional.get());
    }

    public Weather getWeatherFromWeatherStackApi(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_URL + city, String.class);

        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
            return saveWeather(weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Weather saveWeather(WeatherResponse weatherResponse) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Weather weather = new Weather(
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localTime(), dateTimeFormatter));

        return weatherRepository.save(weather);
    }
}
