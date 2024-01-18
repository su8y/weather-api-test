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
import java.util.List;

@Service
public class WeatherService {
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
        double[] coords = weatherRequest.coords();
        double[] transitedXY = WeatherUtils.transXY(coords[0], coords[1]);

        String o =(String) forecastInquiryAPI.currentWeatherInfoBetween1And3Days(transitedXY[0], transitedXY[1]);
        JsonNode jsonNode = objectMapper.readTree(o);

        int value = 0;
        String value1 = "";
        int value2 = 0;
        int value3 = 0;
        String value4 = "";
        JsonNode jsonNode1 = jsonNode.findValue("item");
        for (int i = 0; i < jsonNode1.size(); i++) {
            JsonNode findNode = jsonNode1.get(i);
            String cate = findNode.get("category").asText();

            if(cate.equals("POP")) value = findNode.get("fcstValue").asInt();
            if(cate.equals("PTY")) value1 = findNode.get("fcstValue").asText();
            if(cate.equals("TMX")) value2 = findNode.get("fcstValue").asInt();
            if(cate.equals("TMN")) value3 = findNode.get("fcstValue").asInt();
            if(cate.equals("SKY"))value4 = findNode.get("fcstValue").asText();
        }
//        for (JsonNode node : category) {
//            String cate = node.asText();
//            if(cate.equals("POP")){
//                value = node.findParent("fcstValue").asInt();
//                System.out.println(value);
//            }
//            if(cate.equals("PTY")) value1 = node.findParent("fcstValue").asText();
//            if(cate.equals("TMX")) value2 = node.findParent("fcstValue").asInt();
//            if(cate.equals("TMN")) value3 = node.findParent("fcstValue").asInt();
//            if(cate.equals("SKY")){
//                System.out.println("SKY FOUND");
////                System.out.println(node.findParent("fcstValue"));
//                System.out.println(node.findValue("fcstValue"));
//                value4 = node.findParent("fcstValue").asText();
//            }
//        }

        Weather weather = new Weather();
        weather.setPTV(value1);
        weather.setMaxTemperature(value2);
        weather.setMinTemperature(value3);
        weather.setAmSkyCondition(value4);
        weather.setAmRainyPercentage(value);

        return weather;
    }

    public Weather getMiddleTermWeather(WeatherRequest weatherRequest) throws IOException, InterruptedException, URISyntaxException {
        double[] coords = weatherRequest.coords();
        Region region = geocodingAPI.r_geoCode(coords);
        // 육상코드
        String lc = districtCodeService.getLandCode(region);
        // 기온코드
        String tc = districtCodeService.getTemperatureForecastAreaCode(region);

        int dayOfMonth = weatherRequest.date().getDayOfMonth();
        int dayOfMonth1 = LocalDate.now().getDayOfMonth();
        int targetDate = dayOfMonth - dayOfMonth1;
        if(targetDate < 3) throw new IllegalArgumentException("3일 이전의 데이터는 찾을 수 없습니다.");

        String taString = (String) forecastInquiryAPI.futureTemperatureWeatherInfoBetween3And10Days(tc);
        JsonNode taNode = objectMapper.readTree(taString);

        String amDate = String.valueOf(targetDate);
        String pmDate = String.valueOf(targetDate);

        if (targetDate < 8) {
            amDate += "Am";
            pmDate += "Pm";
        }
        String landString = (String) forecastInquiryAPI.futureLandWeatherInfoBetween3And10Days(lc);
        JsonNode landNode = objectMapper.readTree(landString);

        int amRainy = landNode.findValue("rnSt" + amDate).asInt();
        int pmRainy = landNode.findValue("rnSt" + pmDate).asInt();
        String amWeatherState = landNode.findValue("wf" + amDate).asText();
        String pmWeatherState = landNode.findValue("wf" + pmDate).asText();

        int minTemperature = taNode.findValue("taMin" + targetDate).asInt();
        int maxTemperature = taNode.findValue("taMax" + targetDate).asInt();

        Weather weather = new Weather();
        weather.setAmRainyPercentage(amRainy);
        weather.setPmRainyPercentage(pmRainy);
        weather.setAmSkyCondition(amWeatherState);
        weather.setPmSkyCondition(pmWeatherState);
        weather.setMinTemperature(minTemperature);
        weather.setMaxTemperature(maxTemperature);


        return weather;
    }
}
