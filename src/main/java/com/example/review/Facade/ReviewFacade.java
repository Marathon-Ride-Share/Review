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
            // 检查当前用户是否已经对当前的行程发表过评论
            ServerResponse<Review> existingReview = reviewService.getReviewByUserIdAndRideId(postReviewReq.getUserId(), postReviewReq.getRideId());
            if (existingReview.getData() != null) {
                return new ServerResponse<>(StatusCode.BAD_REQUEST, "You have already reviewed this ride", null);
            }

            // 检查当前用户是否是这个行程的乘客
            RideHistory rideHistory = rideService.getRideByUserId(postReviewReq.getUserId());
            if (!rideHistory.getPassengerRideIds().contains(postReviewReq.getRideId())) {
                return new ServerResponse<>(StatusCode.FORBIDDEN, "You are not a passenger of this ride", null);
            }

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
    ServerResponse<String> authorId = reviewService.getAuthorIdByReviewId(reviewId);
    if (!userId.equals(authorId.getData())) {
        return new ServerResponse<>(StatusCode.FORBIDDEN, "You do not have permission to update this review", null);
    }

    ServerResponse<Review> updatedReview = reviewService.updateReview(reviewId, postReviewReq);
    if (updatedReview.getStatusCode() == StatusCode.SUCCESS) {
        // 如果评论更新成功，重新计算平均评分并更新司机的评分
        ServerResponse<Float> avgRating = reviewService.calculateAverageRating(updatedReview.getData().getDriverId());
        userService.setDriverRating(updatedReview.getData().getDriverId(), avgRating.getData());
    }

    return updatedReview;
}

    public ServerResponse<Review> deleteReview(String reviewId, String userId) {
        ServerResponse<String> authorId = reviewService.getAuthorIdByReviewId(reviewId);
        if (!userId.equals(authorId.getData())) {
            return new ServerResponse<>(StatusCode.FORBIDDEN, "You do not have permission to delete this review", null);
        }

        ServerResponse<Review> deletedReview = reviewService.deleteReview(reviewId);
        if (deletedReview.getStatusCode() == StatusCode.SUCCESS) {
            // 如果评论删除成功，重新计算平均评分并更新司机的评分
            ServerResponse<Float> avgRating = reviewService.calculateAverageRating(deletedReview.getData().getDriverId());
            userService.setDriverRating(deletedReview.getData().getDriverId(), avgRating.getData());
        }

        return deletedReview;
    }

    public ServerResponse<List<Review>> getReviewByUserId(String userId) {
        return reviewService.getReviewByUserId(userId);
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
        rideSummary.setLicensePlate(ride.getVehicleInfo().getLicensePlate());
        review.setRideSummary(rideSummary);
        return review;
    }
}
