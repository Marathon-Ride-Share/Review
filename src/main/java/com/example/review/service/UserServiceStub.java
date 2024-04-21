package com.example.review.service;

import com.example.review.dto.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Author: hhx
 * @Date: 2024/4/19 12:45
 * @Description:
 */
@Component
public class UserServiceStub {

    public User getUserById(String userId) {
        User user = new User();
        user.setUsername(userId);
        user.setDl_info("Test DL Info");
        user.setMake("Test Make");
        user.setModel("Test Model");
        user.setColor("Test Color");
        user.setPlate_number("Test Plate Number");
        user.setRating(4.5f);

        return user;
    }

    public User setDriverRating(String userId, float rating) {
        User user = new User();
        user.setUsername(userId);
        user.setDl_info("Test DL Info");
        user.setMake("Test Make");
        user.setModel("Test Model");
        user.setColor("Test Color");
        user.setPlate_number("Test Plate Number");
        user.setRating(rating);

        return user;
    }
}
