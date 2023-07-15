package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.FollowAccount;
import com.example.follow.model.response.ResultCode;
import com.example.follow.repository.FollowAccountRepository;
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
public class FollowAccountServiceImpl implements FollowAccountService {
    @Autowired
    private FollowAccountRepository followAccountRepository;
    @Autowired
    private SecurityUser securityUser;

    @Override
    public FollowAccount createOrSave(int type, String account) {
        if (TextUtil.isEmpty(account)) {
            throw new BusinessException("账户不能为空");
        }
        FollowAccount followAccount = findByType(type, securityUser.getUserId());
        if (followAccount != null) {
            followAccount.setAccount(account);
        } else {
            followAccount = new FollowAccount();
            followAccount.setUserId(securityUser.getUserId());
            followAccount.setFollowType(type);
            followAccount.setAccount(account);
            // 默认加十个被关注
            followAccount.setNeedFollowedCount(10);
        }
        return followAccountRepository.save(followAccount);
    }

    @Override
    public List<FollowAccount> findAll() {
        return followAccountRepository.findAllByUserId(securityUser.getUserId());
    }

    @Override
    public FollowAccount saveByCount(FollowAccount followAccount) {
        FollowAccount follow = findByType(followAccount.getFollowType(), followAccount.getUserId());
        if (follow == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "不允许修改");
        }
        follow.setFollowCount(followAccount.getFollowCount());
        follow.setNeedFollowedCount(followAccount.getNeedFollowedCount());
        follow.setFollowedCount(followAccount.getFollowedCount());
        return followAccountRepository.save(follow);
    }

    @Override
    public FollowAccount findByType(int type, long userId) {
        return followAccountRepository.findByFollowTypeAndUserId(type, userId);
    }
}
