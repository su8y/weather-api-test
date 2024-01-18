package com.example.geomweatherapi.infrastructure.api;

import com.example.geomweatherapi.domain.vo.Region;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 좌표계 값을 통한 역 지오코딩으로 주소 값을 가져오는 API.
 */
@Repository
public class GeocodingAPI {

    private final ObjectMapper objectMapper;

    public GeocodingAPI(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public String geocode(double[] coords) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        String coordsToStr = Arrays.stream(coords).mapToObj(String::valueOf).limit(2).collect(Collectors.joining(","));

        URI uri = URI.create("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?output=json&coords=" + coordsToStr);
        HttpRequest build = HttpRequest.newBuilder().GET()
                .GET()
                .uri(uri)
                .setHeader("X-NCP-APIGW-API-KEY-ID", "pbvp2xhupu")
                .setHeader("X-NCP-APIGW-API-KEY", "3MMmJ9aYW0d7AD3G9GSQXIFO8nC4gZOnH4ZfLkYn")
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public Region r_geoCode(double[] coords) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        String coordsToStr = Arrays.stream(coords).mapToObj(String::valueOf).limit(2).collect(Collectors.joining(","));

        URI uri = URI.create("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?output=json&coords=" + coordsToStr);
        HttpRequest build = HttpRequest.newBuilder().GET()
                .GET()
                .uri(uri)
                .setHeader("X-NCP-APIGW-API-KEY-ID", "pbvp2xhupu")
                .setHeader("X-NCP-APIGW-API-KEY", "3MMmJ9aYW0d7AD3G9GSQXIFO8nC4gZOnH4ZfLkYn")
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonNode = objectMapper.readTree(response.body());
        JsonNode result = jsonNode.get("results").get(0).get("region");
        String area0 = result.get("area0").get("name").asText();
        String area1 = result.get("area1").get("name").asText();
        String area2 = result.get("area2").get("name").asText();
        return new Region(area0, area1, area2);
    }
}
