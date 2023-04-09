package com.marulov.weathertracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Weather Tracker API")
                        .version("1.0.0")
                        .description("This is an api providing weather data for last 30 minutes for a given city.")
                );
    }
}