package com.example.follow.service;

import com.example.follow.model.user.User;

import java.util.HashMap;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
public interface UserService {
    User findUserByPhone(String phone);

    User findUserById(long userId);

    HashMap<String, Object> login(User user);


    User createUser(User user);

    User updateUser(User user, User saveUser);

    void logout();

    void sendCode(String email, String phone);

    /**
     * 修改密码
     *
     * @param code
     * @param user
     */
    User changePsw(String code, User user);



    long totalUserCount();

    void updateFollow();
}
