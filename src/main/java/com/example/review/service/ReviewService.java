package com.example.review.service;
import com.example.review.common.ServerResponse;
import com.example.review.common.StatusCode;
import com.example.review.dto.PostReviewReq;
import com.example.review.model.Review;
import com.example.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Author: hhx
 * @Date: 2024/4/16 16:49
 * @Description:
 */
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ServerResponse<Review> addReview(Review review) {
        try {
            Review savedReview = reviewRepository.save(review);
            return new ServerResponse<>(StatusCode.SUCCESS, "Review added successfully", savedReview);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while adding the review", null);
        }
    }

    public ServerResponse<List<Review>> getReviewsByRideId(String rideId) {
        try {
            List<Review> reviews = reviewRepository.getReviewsByRideId(rideId);
            return new ServerResponse<>(StatusCode.SUCCESS, "Reviews retrieved successfully", reviews);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while retrieving the reviews", null);
        }
    }

    public ServerResponse<Float> calculateAverageRating(String driverId) {
        try {
            List<Review> reviews = reviewRepository.findByDriverId(driverId);
            double sum = reviews.stream()
                    .mapToDouble(Review::getRating)
                    .sum();
            float averageRating = (float) (sum / reviews.size());
            return new ServerResponse<>(StatusCode.SUCCESS, "Average rating calculated successfully", averageRating);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while calculating the average rating", null);
        }
    }

    public ServerResponse<Review> updateReview(String reviewId, PostReviewReq postReviewReq) {
        try {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NoSuchElementException("No review found with id: " + reviewId));
            review.setComment(postReviewReq.getComment());
            review.setRating(postReviewReq.getRating());
            Review updatedReview = reviewRepository.save(review);
            return new ServerResponse<>(StatusCode.SUCCESS, "Review updated successfully", updatedReview);
        } catch (NoSuchElementException e) {
            return new ServerResponse<>(StatusCode.NOT_FOUND, e.getMessage(), null);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while updating the review", null);
        }
    }

    public ServerResponse<Review> findById(String reviewId) {
        try {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NoSuchElementException("No review found with id: " + reviewId));
            return new ServerResponse<>(StatusCode.SUCCESS, "Review retrieved successfully", review);
        } catch (NoSuchElementException e) {
            return new ServerResponse<>(StatusCode.NOT_FOUND, e.getMessage(), null);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while retrieving the review", null);
        }
    }

    public ServerResponse<Review> deleteReview(String reviewId) {
        try {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NoSuchElementException("No review found with id: " + reviewId));
            reviewRepository.delete(review);
            return new ServerResponse<>(StatusCode.SUCCESS, "Review deleted successfully", review);
        } catch (NoSuchElementException e) {
            return new ServerResponse<>(StatusCode.NOT_FOUND, e.getMessage(), null);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while deleting the review", null);
        }
    }

    public ServerResponse<String> getAuthorIdByReviewId(String reviewId) {
        try {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NoSuchElementException("No review found with id: " + reviewId));
            return new ServerResponse<>(StatusCode.SUCCESS, "Author id retrieved successfully", review.getUserId());
        } catch (NoSuchElementException e) {
            return new ServerResponse<>(StatusCode.NOT_FOUND, e.getMessage(), null);
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while retrieving the author id", null);
        }
    }

    public ServerResponse<Review> getReviewByUserIdAndRideId(String userId, String rideId) {
        try {
            Review review = reviewRepository.findByUserIdAndRideId(userId, rideId);
            if (review != null) {
                return new ServerResponse<>(StatusCode.SUCCESS, "Review retrieved successfully", review);
            } else {
                return new ServerResponse<>(StatusCode.NOT_FOUND, "No review found for this user and ride", null);
            }
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while retrieving the review", null);
        }
    }

    public ServerResponse<List<Review>> getReviewByUserId(String userId) {
        try {
            List<Review> reviews = reviewRepository.findByUserId(userId);
            if (!reviews.isEmpty()) {
                return new ServerResponse<>(StatusCode.SUCCESS, "Reviews retrieved successfully", reviews);
            } else {
                return new ServerResponse<>(StatusCode.NOT_FOUND, "No reviews found for this user", null);
            }
        } catch (Exception e) {
            return new ServerResponse<>(StatusCode.INTERNAL_SERVER_ERROR, "An error occurred while retrieving the reviews", null);
        }
    }
}
