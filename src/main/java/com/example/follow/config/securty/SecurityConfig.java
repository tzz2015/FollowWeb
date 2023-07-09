package com.example.follow.config.securty;

import com.example.follow.except.CustomAccessDeniedHandler;
import com.example.follow.except.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/8
 **/

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    // jwt 校验过滤器，从 http 头部 Authorization 字段读取 token 并校验
    @Bean
    public JwtAuthenticationTokenFilter authFilter() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 基于 token，不需要 csrf
                .csrf().disable()
                // 基于 token，不需要 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 前后端分离架构不需要csrf保护
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 允许所有OPTIONS请求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(NoneAuthedResources.BACKEND_RESOURCES).permitAll()
                        // 允许直接访问授权登录接口
                        //一般浏览器访问url为get请求，而前端请求设置中为post，增加安全
                        .requestMatchers(HttpMethod.POST, "/user/account/token/").permitAll()
                        // 允许 SpringMVC 的默认错误地址匿名访问
                        .requestMatchers("/error").permitAll()
                        // 其他所有接口必须有Authority信息，Authority在登录成功后的UserDetailsImpl对象中默认设置“ROLE_USER”
                        //.requestMatchers("/**").hasAnyAuthority("ROLE_USER")
                        // 所有请求将会拦截，除了已经登录
                        .anyRequest().authenticated()

                );
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        //这里默认为security登录页面，在后续需要关闭时进行
        // 添加 JWT 过滤器，JWT 过滤器在用户名密码认证过滤器之前
        http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
        //使配置生效
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 将前端请求全双工放行
        //使用WebSecurity.ignoring()忽略某些URL请求，这些请求将被Spring Security忽略，这意味着这些URL将有受到 CSRF、XSS、Clickjacking 等攻击的可能。
        return (web) -> web.ignoring().requestMatchers("/websocket/**");
    }

    /**
     * 配置跨源访问(CORS)
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }


}
