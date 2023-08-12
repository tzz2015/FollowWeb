package com.example.follow.except;

import com.alibaba.fastjson.JSON;
import com.example.follow.model.response.Result;
import com.example.follow.model.response.ResultCode;
import com.example.follow.model.response.ResultResponse;
import com.example.follow.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/9
 **/
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        Result failure = ResultResponse.failure(ResultCode.UNAUTHORIZED);
        String json = JSON.toJSONString(failure);
        WebUtils.renderString(response, json);
    }
}
