package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.user.User;
import com.example.follow.model.user.UserRoles;
import com.example.follow.repository.UserRepository;
import com.example.follow.utils.Constants;
import com.example.follow.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        User findUser = userRepository.findUserByPhoneAndPassword(user.getPhone(), user.getPassword());
        if (findUser == null) {
            throw new BusinessException("用户不存在");
        }
        String jwt = JwtUtil.createJWT(findUser.getPhone());
        System.out.println(jwt);
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone",findUser.getPhone());
        map.put("username",findUser.getUsername());
        map.put("email",findUser.getEmail());
        map.put(Constants.HEADER_STRING,jwt);
        return map;
    }

    @Override
    public User createUser(User user) {
        User userByPhone = findUserByPhone(user.getPhone());
        if (userByPhone != null) {
            throw new BusinessException("用户已存在");
        }
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


}
