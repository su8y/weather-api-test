package com.example.geomweatherapi.interfaces.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record WeatherRequest(
        @JsonProperty("status.code.name")
        double[] coords,
        String CrsType,
        LocalDate date
) {
}
