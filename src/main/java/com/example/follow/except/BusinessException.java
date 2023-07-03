package com.example.follow.except;

import org.springframework.http.HttpStatus;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
public class BusinessException extends RuntimeException {
    private int errorCode;
    private String errorMessage;

    public BusinessException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorMessage) {
        super(errorMessage);
        this.errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}