package com.example.follow.config;

import com.example.follow.except.BusinessException;
import com.example.follow.model.response.Result;
import com.example.follow.model.response.ResultCode;
import com.example.follow.model.response.ResultResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/8
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return ResultResponse.failure(ResultCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        return ResultResponse.failure(ResultCode.INTERNAL_SERVER_ERROR,e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        if (e.getErrorMessage() == null || Objects.equals(e.getErrorMessage().trim(), "")) {
            return ResultResponse.failure(e.getErrorCode());
        } else {
            return ResultResponse.failure(e.getErrorCode(), e.getMessage());
        }
    }
}
