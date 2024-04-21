package com.example.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: hhx
 * @Date: 2024/4/20 12:28
 * @Description:
 */
@Data
@AllArgsConstructor
public class Vehicle {
    private String make;
    private String model;
    private String color;
    private String licensePlate;
}