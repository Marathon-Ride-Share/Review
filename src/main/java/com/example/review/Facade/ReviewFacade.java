package com.example.review.Facade;

import com.example.review.dto.*;
import com.example.review.service.*;
import org.springframework.stereotype.Component;
import com.example.review.model.Review;

import java.util.ArrayList;
import java.util.List;


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

//    private final UserServiceStub userServiceStub;
//    private final RideServiceStub rideServiceStub;


//
//    public ReviewFacade(UserServiceStub userServiceStub, RideServiceStub rideServiceStub, ReviewService reviewService) {
//        this.userServiceStub = userServiceStub;
//        this.rideServiceStub = rideServiceStub;
//        this.reviewService = reviewService;
//    }



    public Review addReview(PostReviewReq postReviewReq) {
        Ride ride = rideService.getRideByRideId(postReviewReq.getRideId());

        Review review = createReview(postReviewReq, ride);

        reviewService.addReview(review);

        float avgRating = reviewService.calculateAverageRating(review.getDriverId());

        User user = userService.setDriverRating(review.getDriverId(), avgRating);

        return review;
    }

    //return ride history detail List both as an passenger and driver by user id
    //need to call ride service to get RideHistory:driverRideIds, passengerRideIds;
    //for each rideId in driverRideIds, call ride service to get ride detail and store&return  RideHistoryDetail
    public RideHistoryDetail displayRideHistoryList(String userId) {
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

        // 创建 RideHistoryDetail 对象并设置 driverRides 和 passengerRides
        RideHistoryDetail rideHistoryDetail = new RideHistoryDetail();
        rideHistoryDetail.setDriverRides(driverRides);
        rideHistoryDetail.setPassengerRides(passengerRides);

        return rideHistoryDetail;

    }



    public RideReview displayRideAndReviews(String rideId) {
        Ride ride = rideService.getRideByRideId(rideId);
        List<Review> reviews = reviewService.getReviewsByRideId(rideId);

        RideReview rideReview = new RideReview();
        rideReview.setRide(ride);
        rideReview.setReviews(reviews);

        return rideReview;
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
