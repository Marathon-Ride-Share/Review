package com.example.review.dto;

import lombok.Data;

/**
 * @Author: hhx
 * @Date: 2024/4/19 22:02
 * @Description:
 */
@Data
public class User {
    private String username;
    private String dl_info;
    private String make;
    private String model;
    private String color;
    private String plate_number;
    private float rating;
}