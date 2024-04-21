package com.example.review.config;

import com.example.review.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author: hhx
 * @Date: 2024/4/20 17:51
 * @Description:
 */
@Configuration
public class UserServiceConfig {
    @Value("${user.service.url}")
    private String userServiceUrl;

    @Bean
    public WebClient.Builder userWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public UserService userService(WebClient.Builder userWebClientBuilder) {
        return new UserService(userWebClientBuilder.baseUrl(userServiceUrl).build());
    }
}
