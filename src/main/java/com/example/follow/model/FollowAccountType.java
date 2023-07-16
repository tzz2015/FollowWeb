package com.example.follow.model;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/8
 **/
@Retention(SOURCE)
public @interface FollowAccountType {
    int DOU_YIN = 0;
    int X_HONG_SHU = 1;

}
