package com.example.follow.service;

import com.example.follow.config.securty.SecurityConfig;
import com.example.follow.except.BusinessException;
import com.example.follow.model.FollowAccountType;
import com.example.follow.model.user.User;
import com.example.follow.model.user.UserRoles;
import com.example.follow.repository.UserRepository;
import com.example.follow.utils.Constants;
import com.example.follow.utils.FormatUtil;
import com.example.follow.utils.JwtUtil;
import com.example.follow.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


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
    @Autowired
    private FollowService followService;
    @Autowired
    private SecurityUser securityUser;
    @Autowired
    private EmailService emailService;

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
        if (findUser == null && FormatUtil.isEmail(user.getPhone())) {
            findUser = userRepository.findUserByEmail(user.getPhone());
        }
        if (findUser == null) {
            throw new BusinessException("用户不存在");
        }
        if (!securityConfig.passwordEncoder().matches(user.getPassword(), findUser.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        logout();
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
        if (!FormatUtil.isMobile(user.getPhone())) {
            throw new BusinessException("手机号码格式不正确");
        }
        if (!FormatUtil.isPassword(user.getPassword())) {
            throw new BusinessException("请输入6-20位由字母和数字组成的密码");
        }

        if (TextUtil.isEmpty(user.getEmail()) || !FormatUtil.isEmail(user.getEmail())) {
            throw new BusinessException("邮箱格式不正确");
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

    /**
     * 修改手机号码 邮箱 密码
     *
     * @param user
     * @return
     */
    public User updateUser(User user, User saveUser) {
//        User loginUser = securityUser.getLoginUser();
        if (saveUser != null) {
            if (TextUtil.isNotEmpty(user.getPhone())) {
                if (!FormatUtil.isMobile(user.getPhone())) {
                    throw new BusinessException("手机号码格式不正确");
                }
                saveUser.setPhone(user.getPhone());
            }
            if (TextUtil.isNotEmpty(user.getEmail()) && FormatUtil.isEmail(user.getEmail())) {
                saveUser.setEmail(user.getEmail());
            }
            if (TextUtil.isNotEmpty(user.getPassword())) {
                if (!FormatUtil.isPassword(user.getPassword())) {
                    throw new BusinessException("请输入6-20位由字母和数字组成的密码");
                }
                String encodePsw = securityConfig.passwordEncoder().encode(user.getPassword());
                saveUser.setPassword(encodePsw);
            }
            return userRepository.save(saveUser);
        } else {
            throw new BusinessException("用户不存在");
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

    @Override
    public void sendCode(String phone, String email) {
        if (!FormatUtil.isEmail(email)) {
            throw new BusinessException("邮箱格式不正确");
        }
        User user = userRepository.findByPhoneAndEmail(phone, email);
        if (user == null) {
            throw new BusinessException("手机号码绑定的邮箱不正确");
        }
        emailService.clearVerificationCode(user.getEmail());
        emailService.sendVerificationCode(email);
    }

    /**
     * 修改密码
     *
     * @param code
     * @param user
     */
    @Override
    public User changePsw(String code, User user) {
        if (TextUtil.isEmpty(code)) {
            throw new BusinessException("验证码不能为空");
        }
        if (!FormatUtil.isEmail(user.getEmail())) {
            throw new BusinessException("邮箱错误");
        }
        User findUser = userRepository.findByPhoneAndEmail(user.getPhone(), user.getEmail());
        if (findUser == null) {
            throw new BusinessException("手机号码绑定的邮箱不正确");
        }
        if (emailService.verifyVerificationCode(user.getEmail(), code)) {
            emailService.clearVerificationCode(user.getEmail());
            return updateUser(user, findUser);
        } else {
            throw new BusinessException("验证码错误");
        }
    }


    @Override
    public long totalUserCount() {
        return userRepository.count();
    }

    @Override
    public void updateFollow() {
        followService.calibrateFollowCount(FollowAccountType.DOU_YIN);
    }

    /**
     * 分页查找用户
     *
     * @param page
     * @param pageCount
     * @return
     */
    @Override
    public List<User> findAllUser(int page, int pageCount) {
        Pageable pageable = PageRequest.of(page, pageCount);
        Page<User> findList = userRepository.findAll(pageable);
        return findList.getContent();
    }


}
