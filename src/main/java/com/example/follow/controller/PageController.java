package com.example.follow.controller;

import com.example.follow.model.response.ResponseResult;
import com.example.follow.model.response.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/8
 **/
@RestController
@ResponseResult
@RequestMapping("/api")
public class PageController {
    @GetMapping("/")
    public String test() {
        return ResultCode.SUCCESS.getMessage();
    }
}
