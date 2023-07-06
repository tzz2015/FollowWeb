package com.example.follow.controller;

import com.example.follow.model.User;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.model.response.ResultCode;
import com.example.follow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
@RestController
@RequestMapping("/users")
@ResponseResult
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return ResultCode.SUCCESS.getMessage();
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }


}

