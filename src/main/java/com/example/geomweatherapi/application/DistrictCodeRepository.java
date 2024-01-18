package com.example.geomweatherapi.application;

import java.util.Optional;

public interface DistrictCodeRepository {
    Optional<String> findLandForecastAreaCodeByCity(String city);

    Optional<String> findTemperatureForecastAreaCodeByCity(String city);
}
