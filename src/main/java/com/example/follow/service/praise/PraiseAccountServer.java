package com.example.follow.service.praise;

import com.example.follow.model.praise.PraiseAccountModel;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/22
 **/
public interface PraiseAccountServer {
    /**
     * 获取点赞账户信息
     *
     * @param followType
     * @return
     */
    PraiseAccountModel getPraiseAccount(int followType);

    /**
     * 获取点赞账户信息
     *
     * @param userId
     * @param followType
     * @return
     */
    PraiseAccountModel getPraiseAccount(long userId, int followType);

    /**
     * 增加点赞数量
     *
     * @param userId
     * @param followType
     * @return
     */
    PraiseAccountModel addPraiseCount(long userId, int followType);

    /**
     * 增加被点赞
     *
     * @param userId
     * @param followType
     * @return
     */
    PraiseAccountModel addPraisedCount(long userId, int followType);

    /**
     * 是否可以被点赞
     *
     * @param userId
     * @param followType
     * @return
     */
    boolean isCanPraised(long userId, int followType);

    /**
     * 查找可关注的列表
     *
     * @param followType
     * @return
     */
    List<Long> findEnablePraiseList(int followType);

}
