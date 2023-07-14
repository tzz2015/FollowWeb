package com.example.follow.controller;

import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/14
 **/
@RestController
@RequestMapping("/api")
@ResponseResult
public class UserFollowController {
    @Autowired
    private UserFollowService userFollowService;


}
