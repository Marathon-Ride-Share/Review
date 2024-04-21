package com.example.review.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: hhx
 * @Date: 2024/4/19 19:19
 * @Description:
 */
@Data
@ToString
public class PostReviewReq {
    private String userId;
    private String rideId;
    private String comment;
    private float rating;
}
