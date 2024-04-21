package com.example.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: hhx
 * @Date: 2024/4/20 12:29
 * @Description:
 */
@Data
@AllArgsConstructor
public class DriverInfo {
    private String driverName;
    private float rating;
}