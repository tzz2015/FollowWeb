package com.example.follow.except;

import com.example.follow.model.response.ResultCode;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
public class BusinessException extends RuntimeException {
    private ResultCode errorCode;
    private String errorMessage;

    public BusinessException(ResultCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorMessage) {
        super(errorMessage);
        this.errorCode = ResultCode.INTERNAL_SERVER_ERROR;
        this.errorMessage = errorMessage;
    }

    public BusinessException(ResultCode errorCode) {
        super("");
        this.errorCode = errorCode;
    }

    public ResultCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}