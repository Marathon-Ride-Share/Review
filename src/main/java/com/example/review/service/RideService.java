package com.example.review.service;

import com.example.review.dto.Ride;
import com.example.review.dto.RideDetailResponse;
import com.example.review.dto.RideHistory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author: hhx
 * @Date: 2024/4/19 12:34
 * @Description:
 */
public class RideService {
    private final WebClient webClient;

    private ObjectMapper objectMapper;

    public RideService(WebClient webClient,ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Ride getRideByRideId(String rideId) {
                String dataStr = webClient.get()
                .uri("/rides/{rideId}", rideId)
                .retrieve()
                .bodyToMono(String.class)
                .block();  // block() will wait for the Mono to complete and return the result
        return parseRideFromResponse(dataStr);
    }

    public RideHistory getRideByUserId(String userId) {
        RideHistory dataStr  = webClient.get()
                .uri("/rides/history/{userId}", userId)
                .retrieve()
                .bodyToMono(RideHistory.class)
                .block();  // block() will wait for the Mono to complete and return the result

//        System.out.println("RideHistory getRideByUserId"+dataStr.getDriverRideIds());
        return dataStr;
//        return parseRideFromResponse(dataStr);
    }

    public Ride parseRideFromResponse(String jsonResponse) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);  // 解析整个JSON字符串为JsonNode
            JsonNode rideNode = rootNode.path("ride");  // 获取"ride"这个JSON对象
            Ride ride = objectMapper.treeToValue(rideNode, Ride.class);  // 将"ride"对象映射到Ride类
            return ride;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
