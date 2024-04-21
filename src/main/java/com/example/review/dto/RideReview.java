package com.example.review.dto;

import com.example.review.model.Review;
import lombok.Data;

import java.util.List;

/**
 * @Author: hhx
 * @Date: 2024/4/20 18:11
 * @Description:
 */
@Data
public class RideReview {
    private Ride ride;
    private List<Review> reviews;
}
