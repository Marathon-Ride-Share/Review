package com.example.review.config;

import com.example.review.service.RideService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${rideService.url}")
    private String rideServiceUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public WebClient.Builder rideWebClientBuilder() {
        return WebClient.builder();
    }

//    @Bean
//    public RideService rideService(WebClient.Builder rideWebClientBuilder) {
//        return new RideService(rideWebClientBuilder.baseUrl(rideServiceUrl).build());
//    }

    @Bean
    public RideService rideService() {
        WebClient webClient = rideWebClientBuilder().baseUrl(rideServiceUrl).build();
        return new RideService(webClient, objectMapper);  // 同时传递 WebClient 和 ObjectMapper
    }
}
