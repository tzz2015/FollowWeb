package com.example.follow.model.user;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/8
 **/
@Retention(SOURCE)
public @interface UserRoles {
    String USER = "user";
    String MANAGER = "manager";

}
