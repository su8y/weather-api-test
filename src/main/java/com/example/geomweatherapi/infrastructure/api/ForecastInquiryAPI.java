package com.example.geomweatherapi.infrastructure.api;

import com.example.geomweatherapi.application.WeatherUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Repository
public class ForecastInquiryAPI {

    private static final String VilageFcstUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=3JCbPRBHUzeIZ%2BLEacuhYBY2fIJA47xcfdBcWp7Pp9B6CT2MTJk8rfXXr4wvuptNfK%2BTAYldFBFkREBraWN0Mw%3D%3D&pageNo=1&numOfRows=10&dataType=JSON";
    private static final String MidLandFcstInfoServiceUrl = "https://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=3JCbPRBHUzeIZ%2BLEacuhYBY2fIJA47xcfdBcWp7Pp9B6CT2MTJk8rfXXr4wvuptNfK%2BTAYldFBFkREBraWN0Mw%3D%3D&pageNo=1&numOfRows=10&dataType=JSON";
    private static final String MidTemperatureFcstInfoServiceUrl = "https://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=3JCbPRBHUzeIZ%2BLEacuhYBY2fIJA47xcfdBcWp7Pp9B6CT2MTJk8rfXXr4wvuptNfK%2BTAYldFBFkREBraWN0Mw%3D%3D&pageNo=1&numOfRows=10&dataType=JSON";
    private static final String ZERO_MIN = "00";

    public Object currentWeatherInfoBetween1And3Days(double x, double y) throws URISyntaxException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

        String formattedDate = dateFormatter.format(new Date());
        String formattedTime = "05" + ZERO_MIN;



        String s = VilageFcstUrl +
                "&base_date=" + formattedDate +
                "&base_time=" + formattedTime +
                "&nx=" + (int) x + "&ny=" +(int) y;

        System.out.println(s);
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(new URI(s), String.class);
        System.out.println(forObject);

        return forObject;
    }

    public Object futureTemperatureWeatherInfoBetween3And10Days(String temperatureCode) throws URISyntaxException {
        LocalDate now = LocalDate.now();
//        now.minusDays(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String tmFc = now.format(dateTimeFormatter) + "06" + ZERO_MIN;

        String s = MidTemperatureFcstInfoServiceUrl + "&regId=" + temperatureCode + "&tmFc=" + tmFc;

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(new URI(s), String.class);

        return result;
    }

    public Object futureLandWeatherInfoBetween3And10Days(String landCode) throws URISyntaxException {
        System.out.println("land Code = " + landCode);
        LocalDate now = LocalDate.now();
//        now.minusDays(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String tmFc = now.format(dateTimeFormatter) + "06" + ZERO_MIN;

        String s = MidLandFcstInfoServiceUrl + "&regId=" + landCode + "&tmFc=" + tmFc;

        System.out.println(s);
        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(new URI(s), String.class);

        return result;
    }

    private String getRegId(double x, double y) {
        return "11B00000";
    }
}
