package com.example.follow.model;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/8
 **/
@Retention(SOURCE)
public @interface FollowType {
    /**
     * 抖音互粉
     */
    int DOU_YIN = 0;
    /**
     * 抖音互赞
     */
    int DOU_YIN_PRAISE = 1;

}
