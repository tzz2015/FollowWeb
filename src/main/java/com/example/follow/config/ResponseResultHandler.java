package com.example.follow.config;

import com.alibaba.fastjson.JSON;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.model.response.Result;
import com.example.follow.model.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/6
 **/
@Slf4j

@ControllerAdvice

public class ResponseResultHandler implements ResponseBodyAdvice<Object> {



    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseResult.class;
    /**

     * @param returnType

     * @param converterType

     * @return 此处如果返回false , 则不执行当前Advice的业务

     * 是否请求包含了包装注解 标记，没有直接返回不需要重写返回体，

     */


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE) || returnType.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    /**

     * @param body

     * @param methodParameter

     * @param mediaType

     * @param aClass

     * @param serverHttpRequest

     * @param serverHttpResponse

     * @return 处理响应的具体业务方法

     */

    @Override

    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (body instanceof Result) {

            return body;

        } else if (body instanceof String) {

            return JSON.toJSONString(ResultResponse.success(body));

        } else {

            return ResultResponse.success(body);

        }

    }

}
