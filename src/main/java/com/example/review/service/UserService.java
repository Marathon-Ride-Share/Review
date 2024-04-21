package com.example.review.service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.review.dto.User;

/**
 * @Author: hhx
 * @Date: 2024/4/19 12:34
 * @Description:
 */
public class UserService {
    private final WebClient webClient;

    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }

    public User getUserById(String userId) {
        return webClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }



    public User setDriverRating(String userId,float rating) {
        return webClient.patch()
                .uri("/users/rating/{rating}", rating)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}
