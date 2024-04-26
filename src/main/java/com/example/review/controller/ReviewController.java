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

import java.util.List;

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

    @GetMapping("/users/{userId}")
    public ServerResponse<List<Review>> getReviewByUserId(@PathVariable String userId) {
         return reviewFacade.getReviewByUserId(userId);
    }


    @PostMapping
    public ServerResponse<Review> addReview(@RequestBody PostReviewReq postReviewReq) {
        ServerResponse<Review> review = reviewFacade.addReview(postReviewReq);
        return review;
    }

    @GetMapping("/{userId}/rides")
    public ServerResponse<RideHistoryDetail> displayRideHistoryList( @PathVariable String userId) {
        ServerResponse<RideHistoryDetail> rideHistoryDetail = reviewFacade.displayRideHistoryList(userId);
        return rideHistoryDetail;
    }

    @GetMapping("/{rideId}")
    public ServerResponse<RideReview> displayRideAndReviews( @PathVariable String rideId) {
//        System.out.println("ReviewController displayRideAndReviews rideId: " + rideId);
        return reviewFacade.displayRideAndReviews(rideId);
    }

    @PatchMapping("/{reviewId}")
    public ServerResponse<Review> updateReview(@PathVariable String reviewId, @PathVariable String userId, @RequestBody PostReviewReq postReviewReq) {
        return reviewFacade.updateReview(reviewId, userId, postReviewReq);
    }

    @DeleteMapping("/{reviewId}/{userId}")
    public ServerResponse<Review> deleteReview(@PathVariable String reviewId, @PathVariable String userId) {
//        System.out.println("ReviewController deleteReview reviewId: " + reviewId + " userId: " + userId);
        ServerResponse<Review> review = reviewFacade.deleteReview( reviewId, userId);

        return review;
    }

}
