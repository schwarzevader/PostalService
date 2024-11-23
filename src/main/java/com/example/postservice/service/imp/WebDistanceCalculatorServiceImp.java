package com.example.postservice.service.imp;

import com.example.postservice.service.interfaces.DistanceCalculatorService;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphhopper.GHResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service(value = "webDistanceCalculatorServiceImp")
@Slf4j
public class WebDistanceCalculatorServiceImp implements DistanceCalculatorService {

    @Value("${graphhopper.graphURL}")
    private String url;


    private final WebClient webClient;
    private final ObjectMapper objectMapper;


    public WebDistanceCalculatorServiceImp(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl(url).build();
        this.objectMapper = objectMapper;
    }

    protected Map<String, Object> requestStr(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
        return Map.of(
                "points", new double[][]{{fromLatitude, fromLongitude}, {toLatitude, toLongitude}},
                "snap_preventions", new String[]{"motorway", "ferry", "tunnel"},
                "details", new String[]{"road_class", "surface"},
                "profile", "car",
                "locale", "ru",
                "instructions", true,
                "calc_points", true,
                "points_encoded", false
        );
    }
    private double parseDistanceFromResponse(String response) {

        return 0.0;
    }

    @Override
    public double calculateDistance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
        try {

            String response = webClient.post()
                    .bodyValue(objectMapper.writeValueAsString(
                            requestStr(fromLatitude, fromLongitude, toLatitude, toLongitude)
                    ))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Response received: {}", response);

            return parseDistanceFromResponse(response);
        } catch (Exception e) {
            log.error("Error occurred during distance calculation: {}", e.getMessage(), e);
            throw new RuntimeException("Distance calculation failed", e);
        }
    }



//    @Override
//    public double calculateDistance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
//
//        StringBuilder jsonBuilder = new StringBuilder();
//        jsonBuilder.append("{")
//                .append("\"points\": [")
//                .append(fromLatitude)
//                .append(",")
//                .append(fromLongitude)
//                .append("],")
//                .append("[")
//                .append(toLatitude)
//                .append(",")
//                .append(toLongitude)
//                .append("],")
//                .append("\"snap_preventions\": [\"motorway\", \"ferry\", \"tunnel\"],")
//                .append("\"details\": [\"road_class\", \"surface\"],")
//                .append("\"profile\": \"car\",")
//                .append("\"locale\": \"en\",")
//                .append("\"instructions\": true,")
//                .append("\"calc_points\": true,")
//                .append("\"points_encoded\": false")
//                .append("}");
//
//
//        WebClient webClient = WebClient.builder()
//                .baseUrl(this.url)
//                .defaultHeader("Content-Type", "application/json")
//                .build();
//
//
//        Mono<String> responseMono = webClient.post()
//                .bodyValue(jsonBuilder.toString())
//                .retrieve()
//                .bodyToMono(String.class);
//
//
//        responseMono.subscribe(
//                response -> {
//                    System.out.println("Response Body: " + response);
//                },
//                error -> {
//                    System.err.println("Error occurred: " + error.getMessage());
//                }
//        );
//
//        return 0;
//    }
}
