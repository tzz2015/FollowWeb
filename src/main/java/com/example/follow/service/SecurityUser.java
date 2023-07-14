package com.example.follow.service;

import com.example.follow.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/9
 **/

@Slf4j
@Service
public class SecurityUser{

    @Autowired
    private UserService userService;

    public Long getLoginId() {
        Object phone = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtils.isEmpty(phone)){
            throw new RuntimeException("获取登录人失败");
        }
        return Long.valueOf((String) phone);
    }

    /**
     * 获取当前登录人
     *
     * @return
     */
    public User getLoginUser() {
        return userService.findUserById(getLoginId());
    }
}
