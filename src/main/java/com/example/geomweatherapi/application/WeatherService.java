package com.example.geomweatherapi.application;

import com.example.geomweatherapi.domain.vo.Region;
import com.example.geomweatherapi.infrastructure.api.ForecastInquiryAPI;
import com.example.geomweatherapi.infrastructure.api.GeocodingAPI;
import com.example.geomweatherapi.interfaces.rest.model.Weather;
import com.example.geomweatherapi.interfaces.rest.model.WeatherRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Service
public class WeatherService {

    private static final int THREE_DAYS = 3;
    private static final int MAX_DAYS = 10;
    private static final int EIGHT_DAYS = 8;


    private final DistrictCodeService districtCodeService;
    private final ForecastInquiryAPI forecastInquiryAPI;
    private final GeocodingAPI geocodingAPI;
    private final ObjectMapper objectMapper;


    public WeatherService(DistrictCodeService districtCodeService, ForecastInquiryAPI forecastInquiryAPI, GeocodingAPI geocodingAPI, ObjectMapper objectMapper) {
        this.districtCodeService = districtCodeService;
        this.forecastInquiryAPI = forecastInquiryAPI;
        this.geocodingAPI = geocodingAPI;
        this.objectMapper = objectMapper;
    }

    public Weather getShortTermWeather(WeatherRequest weatherRequest) throws URISyntaxException, JsonProcessingException {
        double[] transitedXY = WeatherUtils.transLLFromXY(weatherRequest.coords());

        String result = (String) forecastInquiryAPI.currentWeatherInfoBetween1And3Days(transitedXY);
        JsonNode jsonNode = objectMapper.readTree(result);

        int rainyPercent = 0;
        String ptv = "";
        int maxTemperature = 0;
        int minTemperature = 0;
        String skyConidtion = "";

        JsonNode itemList = jsonNode.findValue("item");
        for (int i = 0; i < itemList.size(); i++) {
            JsonNode findNode = itemList.get(i);
            String cate = findNode.get("category").asText();

            if (cate.equals("POP")) rainyPercent = findNode.get("fcstValue").asInt();
            if (cate.equals("PTY")) ptv = findNode.get("fcstValue").asText();
            if (cate.equals("TMX")) maxTemperature = findNode.get("fcstValue").asInt();
            if (cate.equals("TMN")) minTemperature = findNode.get("fcstValue").asInt();
            if (cate.equals("SKY")) skyConidtion = findNode.get("fcstValue").asText();
        }

        Weather weather = new Weather();
        weather.setPTV(ptv);
        weather.setMaxTemperature(maxTemperature);
        weather.setMinTemperature(minTemperature);
        weather.setAmSkyCondition(skyConidtion);
        weather.setAmRainyPercentage(rainyPercent);

        return weather;
    }

    public Weather getMiddleTermWeather(WeatherRequest weatherRequest) throws IOException, InterruptedException, URISyntaxException {
        double[] coords = weatherRequest.coords();
        Region region = geocodingAPI.r_geoCode(coords);
        // 육상코드
        String lc = districtCodeService.getLandCode(region);
        // 기온코드
        String tc = districtCodeService.getTemperatureForecastAreaCode(region);

        int targetDate = getTargetDate(weatherRequest.date());

        if (targetDate < THREE_DAYS || targetDate > MAX_DAYS)
            throw new IllegalArgumentException("3일 이전, 8일 이후의 날씨는 찾을 수 검색할 수 없습니다.");

        String taString = (String) forecastInquiryAPI.futureTemperatureWeatherInfoBetween3And10Days(tc);
        JsonNode taNode = objectMapper.readTree(taString);


        String landString = (String) forecastInquiryAPI.futureLandWeatherInfoBetween3And10Days(lc);
        JsonNode landNode = objectMapper.readTree(landString);

        String amDate = String.valueOf(targetDate);
        String pmDate = String.valueOf(targetDate);
        if (targetDate < EIGHT_DAYS) {
            amDate += "Am";
            pmDate += "Pm";
        }

        int amRainy = landNode.findValue("rnSt" + amDate).asInt();
        int pmRainy = landNode.findValue("rnSt" + pmDate).asInt();
        int minTemperature = taNode.findValue("taMin" + targetDate).asInt();
        int maxTemperature = taNode.findValue("taMax" + targetDate).asInt();
        String amWeatherState = landNode.findValue("wf" + amDate).asText();
        String pmWeatherState = landNode.findValue("wf" + pmDate).asText();

        Weather weather = new Weather();
        weather.setAmRainyPercentage(amRainy);
        weather.setPmRainyPercentage(pmRainy);
        weather.setMinTemperature(minTemperature);
        weather.setMaxTemperature(maxTemperature);
        weather.setAmSkyCondition(amWeatherState);
        weather.setPmSkyCondition(pmWeatherState);

        return weather;
    }


    private static int getTargetDate(LocalDate request) {
        int dayOfMonth = request.getDayOfMonth();
        int dayOfMonth1 = LocalDate.now().getDayOfMonth();
        int targetDate = dayOfMonth - dayOfMonth1;

        return targetDate;
    }
}
