package com.example.review.service;

import com.example.review.dto.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: hhx
 * @Date: 2024/4/19 12:46
 * @Description:
 * /**
 *  * RideServiceStub类用于模拟RideService的行为。
 *  * 这个类包含了一些方法，例如getRideDetailById，这些方法返回预定义的RideDetail对象。
 *  * 注意：这只是一个存根类，用于测试和开发过程中，不应在生产环境中使用。
 *  */
@Component
public class RideServiceStub {

    public Ride getRideById(String rideId) {
        // 创建一个Ride对象，并填充一些预定义的数据
        Ride ride = new Ride();
        ride.setRideId(rideId);
        ride.setDriverInfo(new DriverInfo("driver1", 3.21F));
        ride.setVehicle(new Vehicle("BMW", "White", "red", "1234"));
        ride.setStartTime(LocalDateTime.now());
        ride.setEndTime(LocalDateTime.now().plusHours(1));
        ride.setOrigin(new Location( 12.34, 56.78,"OriginName"));
        ride.setDestination(new Location(90.12, 34.56,"DestinationName"));

        float v = 100.00F;
        ride.setPrice(v);
        ride.setStatus("In Progress");
        ride.setAvailableSeats(3);
        ride.setPassengers(List.of(new Passenger("passenger1", new Location(12.34, 56.78, "OriginName"),"paymentOrderId1")));
        // 将Ride对象包装在一个Mono中，并返回
        return ride;
    }
}
