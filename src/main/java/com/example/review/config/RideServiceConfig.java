package com.example.review.config;

import com.example.review.service.RideService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author: hhx
 * @Date: 2024/4/20 17:14
 * @Description:
 */
@Configuration
public class RideServiceConfig {

    @Value("${ride.service.url}")
    private String rideServiceUrl;

    @Bean
    public WebClient.Builder rideWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RideService rideService(WebClient.Builder rideWebClientBuilder) {
        return new RideService(rideWebClientBuilder.baseUrl(rideServiceUrl).build());
    }
}
