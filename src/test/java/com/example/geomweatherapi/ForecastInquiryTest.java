package com.example.geomweatherapi;

import com.example.geomweatherapi.infrastructure.api.ForecastInquiryAPI;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

@SpringBootTest
public class ForecastInquiryTest {
    private static final ForecastInquiryAPI api = new ForecastInquiryAPI();


    @Test
    void 테스트() throws UnsupportedEncodingException, URISyntaxException {
        double x = 37.36145D, y = 127.111544444444D;
        double[] coord = {x, y};

        api.currentWeatherInfoBetween1And3Days(coord);
    }

}
