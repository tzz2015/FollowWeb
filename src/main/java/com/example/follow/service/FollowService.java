package com.example.follow.service;

import com.example.follow.model.Follow;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/15
 **/
public interface FollowService {
    /**
     * 添加记录
     *
     * @param follow
     * @return
     */
    Follow addFollow(Follow follow);

    /**
     * 早期关注的用户
     *
     * @param follow
     * @return
     */
    Follow addEarlyFollow(Follow follow);

    /**
     * 查询记录
     *
     * @param follow
     * @return
     */
    Follow find(Follow follow);

    /**
     * 更新当前用的follow count
     */
    void updateUserFollowCount(int followType);

    /**
     * 更新被关注的用户的 count
     */
    void updateFollowedCount(int followType, long followedUserId);

    /**
     * 校准当前用户的 count
     */
    void calibrateFollowCount(int followType);

    /**
     * 累计关注
     *
     * @return
     */
    long totalCount();

    /**
     * 获取当前用户关注的用户列表
     *
     * @param followType
     * @return
     */
    List<String> getUserFollowAccount(int followType);
}
