package com.example.follow.controller;

import com.example.follow.model.Follow;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
@RestController
@RequestMapping("/api/follow")
@ResponseResult
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/add")
    public Follow update(@RequestBody Follow follow) {
        return followService.addFollow(follow);
    }

    @GetMapping("/count")
    public long totalCount() {
        return followService.totalCount();
    }
}
