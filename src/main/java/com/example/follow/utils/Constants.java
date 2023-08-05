package com.example.follow.utils;

/**
 * @author LYF
 * @dec: 常量
 * @date 2023/7/9
 **/
public class Constants {

    /**
     * 配置令牌环在http heads中的键值
     */
    public static final String HEADER_STRING = "token";

    /**
     * 令牌环头标识
     */
    public static final String TOKEN_PREFIX = "Bearer";

    // 有效期14天
    public static final long JWT_TTL = 60 * 60 * 1000L * 24 * 14;

    // 密钥，内容是随机字符串，长度必须足够长，只能是大小写英文和数字
    public static final String JWT_KEY = "SDFGjhdsfalshdfHFdsjkdsfds121232131afasdfac";
}
