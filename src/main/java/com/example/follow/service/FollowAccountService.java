package com.example.follow.service;

import com.example.follow.model.FollowAccount;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/13
 **/
public interface FollowAccountService {
    /**
     * 添加或者修改关注的账户
     *
     * @param type
     * @param account
     * @return
     */
    FollowAccount createOrSave(int type, String account);

    /**
     * 查找用户所有关注账户
     *
     * @return
     */
    List<FollowAccount> findAll();

    /**
     * 校准关注和被关注的数量
     *
     * @param followAccount
     * @return
     */
    FollowAccount saveByCount(FollowAccount followAccount);

    /**
     * 根据类型查找
     *
     * @param type
     * @return
     */
    FollowAccount findByType(int type, long userId);

    /**
     * 查找可关注的列表
     *
     * @param followType
     * @return
     */
    List<FollowAccount> findEnableFollowList(int followType);
}
