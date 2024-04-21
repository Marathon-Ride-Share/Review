package com.example.review.Facade;

import com.example.review.common.ServerResponse;
import com.example.review.common.StatusCode;
import com.example.review.dto.*;
import com.example.review.service.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.example.review.model.Review;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author: hhx
 * @Date: 2024/4/19 12:33
 * @Description:
 */
@Component
public class ReviewFacade {

    private final ReviewService reviewService;
    private final RideService rideService;
    private final UserService userService;

    public ReviewFacade(ReviewService reviewService, RideService rideService, UserService userService) {
        this.rideService = rideService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    public ServerResponse<Review> addReview(PostReviewReq postReviewReq) {
        try {
            Ride ride = rideService.getRideByRideId(postReviewReq.getRideId());
            Review review = createReview(postReviewReq, ride);
            ServerResponse<Review> savedReview = reviewService.addReview(review);
            ServerResponse<Float> avgRating = reviewService.calculateAverageRating(review.getDriverId());
            User user = userService.setDriverRating(review.getDriverId(), avgRating.getData());

            return new ServerResponse<>(StatusCode.SUCCESS, "Review added successfully", savedReview.getData());
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while adding the review", null);
        }
    }

    public ServerResponse<RideHistoryDetail> displayRideHistoryList(String userId) {
        try {
            RideHistory rideHistory = rideService.getRideByUserId(userId);
            List<Ride> driverRides = new ArrayList<>();
            for (String rideId : rideHistory.getDriverRideIds()) {
                Ride ride = rideService.getRideByRideId(rideId);
                driverRides.add(ride);
            }
            List<Ride> passengerRides = new ArrayList<>();
            for (String rideId : rideHistory.getPassengerRideIds()) {
                Ride ride = rideService.getRideByRideId(rideId);
                passengerRides.add(ride);
            }
            RideHistoryDetail rideHistoryDetail = new RideHistoryDetail();
            rideHistoryDetail.setDriverRides(driverRides);
            rideHistoryDetail.setPassengerRides(passengerRides);
            return new ServerResponse<>(StatusCode.SUCCESS, "Ride history retrieved successfully", rideHistoryDetail);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while retrieving the ride history", null);
        }
    }

    public ServerResponse<RideReview> displayRideAndReviews(String rideId) {
        try {
            Ride ride = rideService.getRideByRideId(rideId);
            ServerResponse<List<Review>> reviews = reviewService.getReviewsByRideId(rideId);
            RideReview rideReview = new RideReview();
            rideReview.setRide(ride);
            rideReview.setReviews(reviews.getData());
            return new ServerResponse<>(StatusCode.SUCCESS, "Ride and reviews retrieved successfully", rideReview);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while retrieving the ride and reviews", null);
        }
    }

    public ServerResponse<Review> updateReview(String reviewId, String userId, PostReviewReq postReviewReq) {
        // 假设你已经有了一个方法可以根据 reviewId 获取评论的作者的 userId
        ServerResponse<String> authorId = reviewService.getAuthorIdByReviewId(reviewId);
        if (!userId.equals(authorId.getData())) {
            return new ServerResponse<>(StatusCode.FORBIDDEN, "You do not have permission to update this review", null);
        }

        return reviewService.updateReview(reviewId, postReviewReq);
    }

    public ServerResponse<Review> deleteReview(String reviewId, String userId) {
        ServerResponse<String> authorId = reviewService.getAuthorIdByReviewId(reviewId);
        if (!userId.equals(authorId.getData())) {
            return new ServerResponse<>(StatusCode.FORBIDDEN, "You do not have permission to update this review", null);
        }

        return reviewService.deleteReview(reviewId);
    }

    private Review createReview(PostReviewReq postReviewReq, Ride ride) {
        Review review = new Review();
        review.setRideId(postReviewReq.getRideId());
        review.setUserId(postReviewReq.getUserId());
        review.setRating(postReviewReq.getRating());
        review.setComment(postReviewReq.getComment());
        review.setDriverId(ride.getDriverInfo().getDriverName());
        Review.RideSummary rideSummary = new Review.RideSummary();
        rideSummary.setStartTime(ride.getStartTime());
        rideSummary.setEndTime(ride.getEndTime());
        rideSummary.setOrigin(ride.getOrigin());
        rideSummary.setDestination(ride.getDestination());
        rideSummary.setPrice(ride.getPrice());
        rideSummary.setLicensePlate(ride.getVehicle().getLicensePlate());
        review.setRideSummary(rideSummary);
        return review;
    }
}
