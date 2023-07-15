package com.example.follow.controller;

import com.example.follow.model.FollowAccount;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/14
 **/
@RestController
@RequestMapping("/api/userFollow")
@ResponseResult
public class UserFollowController {
    @Autowired
    private UserFollowService userFollowService;

    @PostMapping("/updateAccount")
    public FollowAccount update(@RequestBody FollowAccount follow) {
        return userFollowService.createOrSave(follow.getFollowType(), follow.getAccount());
    }
    @PostMapping("/list")
    public List<FollowAccount> findAll(){
        return userFollowService.findAll();
    }
}
