package com.example.review.service;

import com.example.review.dto.Ride;
import com.example.review.dto.RideDetailResponse;
import com.example.review.dto.RideHistory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author: hhx
 * @Date: 2024/4/19 12:34
 * @Description:
 */
public class RideService {
    private final WebClient webClient;

    public RideService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Ride getRideByRideId(String rideId) {
        System.out.println("RideService"+rideId);

                String aaa = webClient.get()
                .uri("/rides/{rideId}", rideId)
                .retrieve()
                .bodyToMono(String.class)
                .block();  // block() will wait for the Mono to complete and return the result

        System.out.println("RideService!!!!! "+aaa);
        return null;
    }

    public RideHistory getRideByUserId(String userId) {
        return webClient.get()
                .uri("/rides/history/{userId}", userId)
                .retrieve()
                .bodyToMono(RideHistory.class)
                .block();
    }

}
