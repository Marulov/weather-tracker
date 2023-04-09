package com.marulov.weathertracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marulov.weathertracker.dto.WeatherDto;
import com.marulov.weathertracker.dto.WeatherResponse;
import com.marulov.weathertracker.model.Weather;
import com.marulov.weathertracker.repository.WeatherRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.marulov.weathertracker.config.WeatherStackConfigProperties.ACCESS_KEY;

@Service
@CacheConfig(cacheNames = {"weathers"})
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    @Cacheable(key = "#city")
    public WeatherDto getWeatherByCity(String city) {

        log.info("Getting weather for city: {}", city);
        Optional<Weather> weatherOptional = weatherRepository.findFirstByRequestedCityOrderByUpdatedTimeDesc(city);

        return weatherOptional.map(weather -> {
            if (weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30)))
                return WeatherDto.convert(getWeatherFromWeatherStackApi(city));
            return WeatherDto.convert(weather);
        }).orElseGet(() -> WeatherDto.convert(getWeatherFromWeatherStackApi(city)));
    }

    public Weather getWeatherFromWeatherStackApi(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getWeatherStackApiUrl(city), String.class);

        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
            return saveWeather(city, weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Weather saveWeather(String city, WeatherResponse weatherResponse) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Weather weather = new Weather(
                weatherResponse.location().name(),
                city,
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localTime(), dateTimeFormatter));

        return weatherRepository.save(weather);
    }

    private String getWeatherStackApiUrl(String city) {
        return String.format("http://api.weatherstack.com/current?access_key=%s&query=%s", ACCESS_KEY, city);
    }

    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRateString = "10000")
    public void clearCache() {
        log.info("Clearing cache");
    }
}
