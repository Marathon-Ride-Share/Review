package com.example.review.controller;
import com.example.review.Facade.ReviewFacade;
import com.example.review.common.ServerResponse;
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
    public ServerResponse<Review> addReview(@RequestBody PostReviewReq postReviewReq) {
        ServerResponse<Review> review = reviewFacade.addReview(postReviewReq);
        return review;
    }

    @GetMapping("/{userId}/rides")
    public ServerResponse<RideHistoryDetail> displayRideHistoryList(String userId) {
        return reviewFacade.displayRideHistoryList(userId);
    }

    @GetMapping("/{rideId}")
    public ServerResponse<RideReview> displayRideAndReviews(String rideId) {
        return reviewFacade.displayRideAndReviews(rideId);
    }

    @PatchMapping("/{reviewId}")
    public ServerResponse<Review> updateReview(@PathVariable String reviewId, @PathVariable String userId, @RequestBody PostReviewReq postReviewReq) {
        return reviewFacade.updateReview(reviewId, userId, postReviewReq);
    }

    @DeleteMapping("/{reviewId}")
    public ServerResponse<Review> deleteReview(@PathVariable String reviewId, @PathVariable String userId) {
        return reviewFacade.deleteReview( reviewId, userId);
    }

}
