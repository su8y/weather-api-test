package com.example.geomweatherapi;


import com.example.geomweatherapi.application.DistrictCodeService;
import com.example.geomweatherapi.domain.vo.Region;
import com.example.geomweatherapi.infrastructure.api.GeocodingAPI;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DistrictCodeServiceTest {
    @Autowired
    private GeocodingAPI geocodingAPi;
    private static ObjectMapper om = new ObjectMapper();

    @Autowired
    private DistrictCodeService districtCodeService;

    @Test
    void 기온코드찾기_테스트() {
        Region region = new Region("kor", "경기도", "성남시");
        String landCode = districtCodeService.getLandCode(region);
        String tempCode = districtCodeService.getTemperatureForecastAreaCode(region);
        System.out.println(landCode + " " + tempCode);

    }

    @Test
    void 육상코드찾기_테스트() {
        Region region = new Region("kor", "서울특별시", "종로구");
        String landCode = districtCodeService.getLandCode(region);
        System.out.println(landCode);

    }

    @Test
    void apiTest() throws IOException, InterruptedException {
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        double[] coords = {129.16166666666666, 36.35394166666667};

        Region region = geocodingAPi.r_geoCode(coords);


        System.out.println(region.toString());
    }

    @Test
    void 정규표현식테스트() {
        String 서울시 = "시울시";
        String 되 = "특별시서울특별시";
        String 되2 = "광역시서울광역시";
        String 시pattern = "[시군구]*$";
        String 도pattern = "특별시$|광역시$";

        String s = 되2.replaceAll("특별시$|광역시$", "");

        String s2 = 서울시.replaceAll("[시군구]*$", "");
        System.out.println(s);
        System.out.println(s2);

    }

}