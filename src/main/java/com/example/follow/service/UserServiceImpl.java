package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.User;
import com.example.follow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public User login(User user) {
        User findUser = userRepository.findUserByPhoneAndPassword(user.getPhone(), user.getPassword());
        if (findUser == null) {
            throw new BusinessException("用户不存在");
        }
        return findUser;
    }

    @Override
    public User createUser(User user) {
        try {
            User userByPhone = findUserByPhone(user.getPhone());
            if (userByPhone != null) {
                throw new BusinessException("用户已存在");
            }
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("注册失败");
        }
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
