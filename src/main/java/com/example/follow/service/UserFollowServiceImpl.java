package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.FollowAccount;
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
    public FollowAccount createOrSave(int type, String account) {
        if (TextUtil.isEmpty(account)) {
            throw new BusinessException("账户不能为空");
        }
        FollowAccount followAccount = findByType(type);
        if (followAccount != null) {
            followAccount.setAccount(account);
        } else {
            followAccount = new FollowAccount();
            followAccount.setUserId(securityUser.getLoginId());
            followAccount.setFollowType(type);
            followAccount.setAccount(account);
        }
        return userFollowRepository.save(followAccount);
    }

    @Override
    public List<FollowAccount> findAll() {
        return userFollowRepository.findAllByUserId(securityUser.getLoginId());
    }

    @Override
    public FollowAccount saveByCount(FollowAccount followAccount) {
        FollowAccount follow = findByType(followAccount.getFollowType());
        if (follow == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "不允许修改");
        }
        follow.setFollowCount(followAccount.getFollowCount());
        follow.setNeedFollowedCount(followAccount.getNeedFollowedCount());
        follow.setFollowedCount(followAccount.getFollowedCount());
        return userFollowRepository.save(follow);
    }

    @Override
    public FollowAccount findByType(int type) {
        return userFollowRepository.findByFollowTypeAndUserId(type, securityUser.getLoginId());
    }
}
