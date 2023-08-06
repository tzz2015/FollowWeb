package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.response.ResultCode;
import com.example.follow.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/9
 **/

@Slf4j
@Service
public class SecurityUser {

    @Autowired
    private UserService userService;

    public Long getUserId() {
        Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        try {
            return NumberUtils.parseNumber(userId.toString(), Long.class);
        } catch (Exception e) {
            return -1L;
        }
    }


    /**
     * 获取当前登录人
     *
     * @return
     */
    public User getLoginUser() {
        if (getUserId() < 0) {
            return null;
        }
        return userService.findUserById(getUserId());
    }
}
