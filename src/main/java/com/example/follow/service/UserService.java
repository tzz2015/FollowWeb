package com.example.follow.service;

import com.example.follow.model.User;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
public interface UserService {
    User findUserByPhone(String phone);

    User login(User user);


    User createUser(User user);

    User updateUser(Long id, User user);
}
