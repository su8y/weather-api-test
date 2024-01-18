package com.example.geomweatherapi;

import com.example.geomweatherapi.application.DistrictCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ExcelTest {

    @Autowired(required = true)
    private DistrictCodeRepository repository;


    @Test
    @DisplayName("엑셀리드_테스트")
    void 엑셀리드_테스트() throws IOException, InterruptedException {
        System.out.println(repository.findLandForecastAreaCodeByCity("서울"));
        System.out.println(repository.findTemperatureForecastAreaCodeByCity("서울"));
    }
}
