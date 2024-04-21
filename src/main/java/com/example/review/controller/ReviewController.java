package com.example.review.controller;
import com.example.review.Facade.ReviewFacade;
import com.example.review.dto.PostReviewReq;
import com.example.review.dto.Ride;
import com.example.review.dto.RideHistoryDetail;
import com.example.review.dto.RideReview;
import com.example.review.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @Author: hhx
 * @Date: 2024/4/16 17:44
 * @Description:
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewFacade reviewFacade;


    @PostMapping
    public Review addReview(@RequestBody PostReviewReq postReviewReq) {
        Review review = reviewFacade.addReview(postReviewReq);
        return review;
    }

    @GetMapping("/{userId}/rides")
    public RideHistoryDetail displayRideHistoryList(String userId) {
        return reviewFacade.displayRideHistoryList(userId);
    }

    @GetMapping("/{rideId}")
    public RideReview displayRideAndReviews(String rideId) {
        return reviewFacade.displayRideAndReviews(rideId);
    }

}
