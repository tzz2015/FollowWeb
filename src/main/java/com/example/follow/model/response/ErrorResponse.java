package com.example.follow.model.response;

import org.springframework.http.HttpStatus;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
public class ErrorResponse extends ApiResponse {
    public ErrorResponse(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public ErrorResponse(int code, String message) {
        super(code, message, null);
    }
}
