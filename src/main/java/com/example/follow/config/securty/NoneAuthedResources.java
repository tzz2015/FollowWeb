package com.example.follow.config.securty;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/9
 **/
public class NoneAuthedResources {
    /**
     * 前端接口资源
     */
    public static final String[] FRONTEND_RESOURCES = {
            // Swagger相关资源
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html/**",
            "/webjars/**",
            "/druid/**",
    };

    /**
     * 不要权限校验的后端接口资源
     * <p>
     * ANT风格的接口正则表达式：
     * <p>
     * ? 匹配任何单字符<br/>
     * * 匹配0或者任意数量的 字符<br/>
     * ** 匹配0或者更多的 目录<br/>
     */
    public static final String[] BACKEND_RESOURCES = {
            // 登录相关接口放开过滤
            "/api/login",
            "/api/create",
            "/api/sendCode",
            "/api/getAdSwitch",
            "/api/changePsw/*",
            "/api/userList",
            "/api/suggestion/add",
            "/api/follow/count",
            "/api/spread/list",
            "/api/announcement/list",
            "/api/praise/count",
            // Swagger及druid相关资源
            "/swagger**/**",
            "/webjars/**",
            "/v2/api-docs",
            "/druid",
    };


}
