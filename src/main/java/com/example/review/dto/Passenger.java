package com.example.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: hhx
 * @Date: 2024/4/20 16:19
 * @Description:
 */
@Data
@AllArgsConstructor
public class Passenger {
    private final String passengerName;
    private final Location pickupLocation;
}
