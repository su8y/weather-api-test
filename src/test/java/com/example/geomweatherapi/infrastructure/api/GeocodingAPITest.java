package com.example.geomweatherapi.infrastructure.api;

import com.example.geomweatherapi.domain.vo.Region;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class GeocodingAPITest {
    @Autowired
    private GeocodingAPI geocodingAPi;
    private static ObjectMapper om = new ObjectMapper();

    @Test
    void apiTest() throws IOException, InterruptedException {
        double[] coords = {129.16166666666666, 36.35394166666667};

        Region region = geocodingAPi.r_geoCode(coords);


        Assertions.assertThat(region.area2()).isEqualTo("청송군");
    }

}