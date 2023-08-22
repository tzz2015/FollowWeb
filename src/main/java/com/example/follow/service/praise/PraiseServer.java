package com.example.follow.service.praise;

import com.example.follow.model.praise.PraiseModel;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/23
 **/
public interface PraiseServer {
    /**
     * 添加记录
     *
     * @param praiseModel
     * @return
     */
    PraiseModel addPraise(PraiseModel praiseModel);

    /**
     * 早期点赞的记录
     *
     * @param praiseModel
     * @return
     */
    PraiseModel addEarlyPraise(PraiseModel praiseModel);

    /**
     * 查询记录
     *
     * @param praiseModel
     * @return
     */
    PraiseModel find(PraiseModel praiseModel);


    /**
     * 累计点赞
     *
     * @return
     */
    long totalCount();

    /**
     * 视频被点赞的数量
     *
     * @param videoId
     * @return
     */
    int videoPraiseCount(long videoId);

    /**
     * 获取当前用户关注的用户列表
     *
     * @param praiseType
     * @return
     */
    List<Long> getUserPraiseAccount(int praiseType);
}
