package com.example.geomweatherapi.interfaces.rest.controller;

import com.example.geomweatherapi.application.DistrictCodeService;
import com.example.geomweatherapi.application.WeatherService;
import com.example.geomweatherapi.interfaces.rest.model.Weather;
import com.example.geomweatherapi.interfaces.rest.model.WeatherRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Controller
@RequestMapping("/apimms/weather/")
public class WeatherController {
    private final WeatherService service;
    private static final long THREE_DAYS = 3L;
    private static final long TEN_DAYS = 10L;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> researchWeather(@ModelAttribute WeatherRequest weatherRequest) throws URISyntaxException, IOException, InterruptedException {
        LocalDate now = LocalDate.now();
        LocalDate targetDate = weatherRequest.date();
        LocalDate addTenDays = now.plusDays(TEN_DAYS);

        if (targetDate.isBefore(now) || targetDate.isAfter(addTenDays)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 날짜의 날씨는 확인할 수 없습니다.");
        }

        LocalDate addThreeDays = now.plusDays(THREE_DAYS);
        Weather result ;
        if (addThreeDays.isBefore(targetDate)) {
            result = service.getShortTermWeather(weatherRequest);
        } else {
            result = service.getMiddleTermWeather(weatherRequest);
        }

        return ResponseEntity.ok(result);
    }
}
