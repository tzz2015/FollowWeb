package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.Follow;
import com.example.follow.model.FollowAccount;
import com.example.follow.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/15
 **/
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private SecurityUser securityUser;
    @Autowired
    private FollowAccountService followAccountService;

    /**
     * 添加记录
     *
     * @param follow
     * @return
     */
    @Transactional
    @Override
    public Follow addFollow(Follow follow) {
        follow.setUserId(securityUser.getUserId());
        Follow findFollow = find(follow);
        if (findFollow != null) {
           return findFollow;
        }
        try {
            Follow save = followRepository.save(follow);
            updateUserFollowCount(follow.getFollowType());
            updateFollowedCount(follow.getFollowType(), follow.getFollowUserId());
            return save;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

    /**
     * 早期关注的用户
     *
     * @param follow
     * @return
     */
    @Transactional
    @Override
    public Follow addEarlyFollow(Follow follow) {
        follow.setEarlyFollowed(true);
        follow.setUserId(securityUser.getUserId());
        Follow findFollow = find(follow);
        if (findFollow != null) {
            return findFollow;
        }
        return  followRepository.save(follow);
    }

    /**
     * 查询记录
     *
     * @param follow
     * @return
     */
    @Override
    public Follow find(Follow follow) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                // 字符串匹配方式
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                // 忽略大小写
                .withIgnoreCase()
                .withIgnoreNullValues();
        Example<Follow> example = Example.of(follow, matcher);
        List<Follow> list = followRepository.findAll(example);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 更新当前用的follow count
     * 每关注一个 FollowAccount needFollowedCount+1 followCount+1
     */
    @Override
    public void updateUserFollowCount(int followType) {
        FollowAccount followAccount = followAccountService.findByType(followType, securityUser.getUserId());
        if (followAccount == null) {
            throw new RuntimeException("当前FollowCount不存在");
        }
        int followCount = followAccount.getFollowCount() + 1;
        int needFollowedCount = followAccount.getNeedFollowedCount() + 1;
        followAccount.setNeedFollowedCount(needFollowedCount);
        followAccount.setFollowCount(followCount);
        followAccountService.saveByCount(followAccount);
    }

    /**
     * 更新被关注的用户的 count
     * 每关注一个 FollowAccount needFollowedCount-1 followedCount+1
     */
    @Override
    public void updateFollowedCount(int followType, long followedUserId) {
        FollowAccount followAccount = followAccountService.checkIsCanFollow(followType, followedUserId);
        if (followAccount == null) {
            throw new BusinessException("当前被关注用户不可被关注");
        }
        int followedCount = followAccount.getFollowedCount() + 1;
        int needFollowedCount = followAccount.getNeedFollowedCount() - 1;
        followAccount.setFollowedCount(followedCount);
        followAccount.setNeedFollowedCount(needFollowedCount);
        followAccountService.saveByCount(followAccount);
    }

    /**
     * 校准当前用户的 count
     * <p>
     * 如果 needFollowedCount ==0 并且 followCount > followedCount  needFollowedCount+=followCount - followedCount
     */
    @Override
    public void calibrateFollowCount(int followType) {
        FollowAccount followAccount = followAccountService.findByType(followType, securityUser.getUserId());
        if (followAccount != null) {
            int needFollowedCount = followAccount.getNeedFollowedCount();
            int followCount = followAccount.getFollowCount();
            int followedCount = followAccount.getFollowedCount();
            if (needFollowedCount == 0 && followCount > followedCount) {
                needFollowedCount = followCount - followedCount;
                followAccount.setNeedFollowedCount(needFollowedCount);
                followAccountService.saveByCount(followAccount);
            }
        }

    }

    /**
     * 累计关注
     *
     * @return
     */
    @Override
    public long totalCount() {
        return followRepository.findAll().size();
    }

    /**
     * 获取当前用户关注的用户列表
     *
     * @param followType
     * @return
     */
    @Override
    public List<String> getUserFollowAccount(int followType) {
        List<String> accountList = new ArrayList<>();
        List<Follow> followList = followRepository.findByUserIdAndFollowType(securityUser.getUserId(), followType);
        for (Follow follow : followList) {
            accountList.add(follow.getFollowAccount());
        }
        return accountList;
    }
}
