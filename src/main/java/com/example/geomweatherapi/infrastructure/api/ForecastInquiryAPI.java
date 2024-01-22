package com.example.geomweatherapi.infrastructure.api;

import com.example.geomweatherapi.application.WeatherUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${myApp.vilage_fcst_url}")
    private String VilageFcstUrl;
    @Value("${myApp.mid_land_fcst_url}")
    private String MidLandFcstInfoServiceUrl;
    @Value("${myApp.mid_temperature_fcst_url}")
    private String MidTemperatureFcstInfoServiceUrl;
    private static final String ZERO_MIN = "00";

    public Object currentWeatherInfoBetween1And3Days(double[] coord) throws URISyntaxException {
        double x = coord[0], y = coord[1];
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

        String formattedDate = dateFormatter.format(new Date());
        String formattedTime = "05" + ZERO_MIN;


        String s = VilageFcstUrl +
                "&base_date=" + formattedDate +
                "&base_time=" + formattedTime +
                "&nx=" + (int) x + "&ny=" + (int) y;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(new URI(s), String.class);
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

}
