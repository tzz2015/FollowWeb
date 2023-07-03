package com.example.follow.model.response;

import org.springframework.http.HttpStatus;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
public class NormalResponse extends ApiResponse {
    public NormalResponse(Object data) {
        super(HttpStatus.OK.value(), "", data);
    }
}
