package com.example.follow.model.response;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/5
 **/
public class ResultResponse {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";


    // 只返回状态

    public static Result success() {
        return new Result()
                .setResult(ResultCode.SUCCESS);

    }


    // 成功返回数据

    public static Result success(Object data) {
        return new Result()
                .setResult(ResultCode.SUCCESS, data);


    }


    // 失败

    public static Result failure(ResultCode resultCode) {
        return new Result()
                .setResult(resultCode);

    }


    // 失败

    public static Result failure(ResultCode resultCode, Object data) {
        return new Result()
                .setResult(resultCode, data);

    }


}

