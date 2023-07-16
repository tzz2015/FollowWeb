package com.example.follow.controller;

import com.example.follow.model.FollowAccount;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.FollowAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public List<FollowAccount> findAll() {
        return followAccountService.findAll();
    }

    @GetMapping("/followList/{followType}")
    public List<FollowAccount> findEnableFollowList(@PathVariable("followType") int followType) {
        return followAccountService.findEnableFollowList(followType);
    }
}
