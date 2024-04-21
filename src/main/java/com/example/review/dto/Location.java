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
public class Location {
    private double latitude;
    private double longitude;
    private String locationName;
}