package com.example.review.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: hhx
 * @Date: 2024/4/19 21:46
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ride {
    private String rideId;
    private Location origin;
    private Location destination;
    private DriverInfo driverInfo;
    private Vehicle vehicle;
    private float price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private int availableSeats;
    private List<Passenger> passengers;
}
