package com.example.review.service;
import com.example.review.model.Review;
import com.example.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: hhx
 * @Date: 2024/4/16 16:49
 * @Description:
 */
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByRideId(String rideId) {
        return reviewRepository.getReviewsByRideId(rideId);
    }

    public float calculateAverageRating(String driverId) {
        List<Review> reviews = reviewRepository.findByDriverId(driverId);
        double sum = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();
        return (float) (sum / reviews.size());
    }
}
