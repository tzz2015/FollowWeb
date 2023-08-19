package com.example.follow.controller;

import com.example.follow.model.AdSwitchModel;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.model.response.ResultCode;
import com.example.follow.model.user.User;
import com.example.follow.service.AdSwitchService;
import com.example.follow.service.SecurityUser;
import com.example.follow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
@RestController
@RequestMapping("/api")
@ResponseResult
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityUser securityUser;
    @Autowired
    private AdSwitchService adSwitchService;


    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user, securityUser.getLoginUser());
    }

    @PostMapping("/sendCode")
    public String sandCode(@RequestBody User user) {
        userService.sendCode(user.getPhone(), user.getEmail());
        return ResultCode.SUCCESS.getMessage();
    }

    @PostMapping("/changePsw/{code}")
    public User changePsw(@PathVariable("code") String code, @RequestBody User user) {
        return userService.changePsw(code, user);
    }

    @PostMapping("/userInfo")
    public User getUser() {
        return securityUser.getLoginUser();
    }

    @PostMapping("/logout")
    public String logout() {
        userService.logout();
        return "退出登录成功";
    }

    @GetMapping("/userList")
    public long totalUserCount() {
        return userService.totalUserCount();
    }

    @PostMapping("/findUserList")
    public List<User> findUserList(@RequestParam("page") int page, @RequestParam("pageCount") int pageCount) {
        return userService.findAllUser(page, pageCount);
    }

    @PostMapping("/updateAdSwitch")
    public AdSwitchModel updateAdSwitch(@RequestBody AdSwitchModel adSwitchModel) {
        return adSwitchService.unpdateAdSwitchModel(adSwitchModel);
    }

    @PostMapping("/getAdSwitch")
    public AdSwitchModel getAdSwitch(@RequestParam("version") String version) {
        return adSwitchService.getAdSwitchModel(version);
    }
}

