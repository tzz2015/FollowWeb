package com.example.follow.controller;

import com.example.follow.model.FollowAccount;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.FollowAccountService;
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
@RequestMapping("/api/followAccount")
@ResponseResult
public class FollowAccountController {
    @Autowired
    private FollowAccountService followAccountService;

    @PostMapping("/updateAccount")
    public FollowAccount update(@RequestBody FollowAccount follow) {
        return followAccountService.createOrSave(follow.getFollowType(), follow.getAccount());
    }
    @PostMapping("/list")
    public List<FollowAccount> findAll(){
        return followAccountService.findAll();
    }
}
