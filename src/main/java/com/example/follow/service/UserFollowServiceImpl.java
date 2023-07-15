package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.UserFollow;
import com.example.follow.model.response.ResultCode;
import com.example.follow.repository.UserFollowRepository;
import com.example.follow.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/14
 **/
@Service
public class UserFollowServiceImpl implements UserFollowService {
    @Autowired
    private UserFollowRepository userFollowRepository;
    @Autowired
    private SecurityUser securityUser;

    @Override
    public UserFollow createOrSave(int type, String account) {
        if (TextUtil.isEmpty(account)) {
            throw new BusinessException("账户不能为空");
        }
        UserFollow userFollow = findByType(type);
        if (userFollow != null) {
            userFollow.setAccount(account);
        } else {
            userFollow = new UserFollow();
            userFollow.setUserId(securityUser.getLoginId());
            userFollow.setFollowType(type);
            userFollow.setAccount(account);
        }
        return userFollowRepository.save(userFollow);
    }

    @Override
    public List<UserFollow> findAll() {
        return userFollowRepository.findAllByUserId(securityUser.getLoginId());
    }

    @Override
    public UserFollow saveByCount(UserFollow userFollow) {
        UserFollow follow = findByType(userFollow.getFollowType());
        if (follow == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "不允许修改");
        }
        follow.setFollowCount(userFollow.getFollowCount());
        follow.setNeedFollowedCount(userFollow.getNeedFollowedCount());
        follow.setFollowedCount(userFollow.getFollowedCount());
        return userFollowRepository.save(follow);
    }

    @Override
    public UserFollow findByType(int type) {
        return userFollowRepository.findByFollowTypeAndUserId(type, securityUser.getLoginId());
    }
}
