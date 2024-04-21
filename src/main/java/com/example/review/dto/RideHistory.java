package com.example.review.dto;
import lombok.Data;

import java.util.List;

/**
 * @Author: hhx
 * @Date: 2024/4/20 10:20
 * @Description:
 */
@Data
public class RideHistory {
    private List<String> driverRideIds;
    private List<String> passengerRideIds;
}