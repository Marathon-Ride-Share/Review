package com.example.review.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.review.dto.Location;

import java.time.LocalDateTime;
import java.util.Date;

@Document
@Data
@AllArgsConstructor
public class Review {
    @Id
    private String reviewId;
    private String rideId;
    private String userId;
    private String driverId;
    private String comment;
    private float rating;

    @CreatedDate
    private LocalDateTime createdAt;
    private RideSummary rideSummary;

    public Review() {
    }

    @Data
    public static class RideSummary {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Location origin; // 类型更改为Location
        private Location destination; // 类型更改为Location
        private float price;
        private String licensePlate; // 新增字段

        public RideSummary() {
        }
    }
}
