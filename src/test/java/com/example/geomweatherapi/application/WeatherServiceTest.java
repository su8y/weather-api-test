package com.example.geomweatherapi.application;

import com.example.geomweatherapi.interfaces.rest.model.Weather;
import com.example.geomweatherapi.interfaces.rest.model.WeatherRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

@SpringBootTest
class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    private WeatherRequest weatherRequest;

    @PostConstruct
    void setup() {
        double x = 126.98935225645432;
        double y = 37.579871128849334;
        weatherRequest = new WeatherRequest(new double[]{x, y}, "EPSG:4326", LocalDate.now());

    }

    @Test
    void shortTermTest() throws URISyntaxException, JsonProcessingException {
        Weather shortTermWeather = weatherService.getShortTermWeather(weatherRequest);

        Assertions.assertThat(shortTermWeather.getAmRainyPercentage()).isNotNull();
    }

    @Test
    void middleTermTest() throws URISyntaxException, IOException, InterruptedException {
        double x = 126.98935225645432;
        double y = 37.579871128849334;
        WeatherRequest weatherRequest = new WeatherRequest(new double[]{x, y}, "EPSG:4326", LocalDate.now().plusDays(8));

        Weather shortTermWeather = weatherService.getMiddleTermWeather(weatherRequest);

        Assertions.assertThat(shortTermWeather.getAmSkyCondition()).isNotBlank();
    }
}