package com.example.follow.service;

import com.example.follow.model.UserFollow;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/13
 **/
public interface UserFollowService {
    /**
     * 添加或者修改关注的账户
     *
     * @param type
     * @param account
     * @return
     */
    UserFollow createOrSave(int type, String account);

    /**
     * 查找用户所有关注账户
     *
     * @param userId
     * @return
     */
    List<UserFollow> findAll(long userId);

    /**
     * 校准关注和被关注的数量
     *
     * @param userFollow
     * @return
     */
    UserFollow saveByCount(UserFollow userFollow);

    /**
     * 根据类型查找
     * @param type
     * @return
     */
    UserFollow findByType(int type);
}
