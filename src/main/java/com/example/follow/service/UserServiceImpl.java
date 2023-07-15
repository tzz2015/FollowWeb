package com.example.follow.service;

import com.example.follow.config.securty.SecurityConfig;
import com.example.follow.except.BusinessException;
import com.example.follow.model.user.User;
import com.example.follow.model.user.UserRoles;
import com.example.follow.repository.UserRepository;
import com.example.follow.utils.Constants;
import com.example.follow.utils.JwtUtil;
import com.example.follow.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityConfig securityConfig;

    @Override
    public User findUserByPhone(String phone) {
        return userRepository.findUserByPhone(phone);
    }

    @Override
    public User findUserById(long userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public HashMap<String, Object> login(User user) {
        User findUser = userRepository.findUserByPhone(user.getPhone());
        if (findUser == null) {
            throw new BusinessException("用户不存在");
        }
        if (!securityConfig.passwordEncoder().matches(user.getPassword(), findUser.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        String jwt = JwtUtil.createJWT(findUser.getPhone());
        System.out.println(jwt);
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", findUser.getPhone());
        map.put("username", findUser.getUsername());
        map.put("email", findUser.getEmail());
        map.put(Constants.HEADER_STRING, jwt);
        return map;
    }

    @Override
    public User createUser(User user) {
        if (TextUtil.isEmpty(user.getPhone()) || TextUtil.isEmpty(user.getPassword())) {
            throw new BusinessException("用户名或者密码为空");
        }
        User userByPhone = findUserByPhone(user.getPhone());
        if (userByPhone != null) {
            throw new BusinessException("用户已存在");
        }
        String encodePassword = securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setRoles(UserRoles.USER);
        return userRepository.save(user);

    }

    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public void logout() {
        // 获取当前用户的认证信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 清除当前用户的认证信息
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        // 执行其他清除操作，例如使会话失效、清除相关的缓存等
    }


}
