package com.example.follow.config.securty;

import com.alibaba.fastjson.JSON;
import com.example.follow.model.response.Result;
import com.example.follow.model.response.ResultCode;
import com.example.follow.model.response.ResultResponse;
import com.example.follow.model.user.User;
import com.example.follow.service.UserService;
import com.example.follow.utils.Constants;
import com.example.follow.utils.JwtUtil;
import com.example.follow.utils.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/8
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    private static final PathMatcher pathmatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        for (String reg : NoneAuthedResources.BACKEND_RESOURCES) {
            if (pathmatcher.match(reg, request.getServletPath())) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String token = request.getHeader(Constants.HEADER_STRING);

        if (!StringUtils.hasText(token) || !token.startsWith(Constants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        String userPhone;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userPhone = claims.getSubject();
        } catch (Exception e) {
            error(response);
            return;
        }

        User user = userService.findUserByPhone(userPhone);

        if (user == null) {
            error(response);
            return;
        }

//        UserDetailsImpl loginUser = new UserDetailsImpl(user);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getId(), null, null);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private void error(HttpServletResponse response) {
        Result failure = ResultResponse.failure(ResultCode.UNAUTHORIZED);
        String json = JSON.toJSONString(failure);
        WebUtils.renderString(response, json);
    }

}
