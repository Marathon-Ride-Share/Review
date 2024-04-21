package com.example.review.common;

public enum StatusCode {
    SUCCESS(200),
    NOT_FOUND(404),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    INTERNAL_SERVER_ERROR(500);


    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
