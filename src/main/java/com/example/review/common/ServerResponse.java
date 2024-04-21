package com.example.review.common;

/**
 * @Author: hhx
 * @Date: 2024/4/20 22:45
 * @Description:
 */

public class ServerResponse<T> {
    private StatusCode statusCode;
    private String message;
    private T data;

    public ServerResponse(StatusCode statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
    // getters and setters
}
