package com.example.review.repository;

import com.example.review.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hhx
 * @Date: 2024/4/16 16:40
 * @Description:
 */

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    @Query("{'driverId': ?0}")
    List<Review> findByDriverId(String driverId);


    // 例如，根据 rideId 查询评论列表
    List<Review> getReviewsByRideId(String rideId);  // 根据行程ID查找评价
}
