package com.example.follow.model.response;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
