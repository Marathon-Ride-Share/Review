package com.example.review.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: hhx
 * @Date: 2024/4/20 17:00
 * @Description:
 */
@Data
public class RideHistoryDetail {
    private List<Ride> driverRides;
    private List<Ride> passengerRides;
}
