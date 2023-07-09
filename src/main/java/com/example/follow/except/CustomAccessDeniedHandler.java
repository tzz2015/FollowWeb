package com.example.follow.except;

import com.alibaba.fastjson.JSON;
import com.example.follow.model.response.Result;
import com.example.follow.model.response.ResultCode;
import com.example.follow.model.response.ResultResponse;
import com.example.follow.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/9
 **/
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        Result failure = ResultResponse.failure(ResultCode.NOT_FOUND);
        String json = JSON.toJSONString(failure);
        WebUtils.renderString(response, json);
    }
}