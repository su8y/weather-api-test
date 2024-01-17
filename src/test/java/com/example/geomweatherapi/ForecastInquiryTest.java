package com.example.geomweatherapi;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.time.LocalDate;

@SpringBootTest
public class ForecastInquiryTest {
    private static final ForecastInquiryAPI api = new ForecastInquiryAPI();


    @Test
    void 테스트() throws UnsupportedEncodingException, URISyntaxException {
        double x = 37.36145D;
        double y = 127.111544444444D;

         api.currentTimeWeatherInfo(x, y);
        api.futureWeatherInfoBetween3And10Days(x, y);
    }

}
