package com.example.follow.service.praise;

import com.example.follow.model.praise.PraiseVideoModel;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/22
 **/
public interface PraiseVideoServer {

    boolean deletePraise(long id);

    /**
     * 添加或者更新
     *
     * @param praiseVideoModel
     * @return
     */
    PraiseVideoModel saveOrUpdate(PraiseVideoModel praiseVideoModel);

    /**
     * 获取视频列表
     *
     * @param followType
     * @return
     */
    List<PraiseVideoModel> findPraiseList(int followType);

    /**
     * 查找可点赞的列表
     *
     * @param followType
     * @return
     */
    List<PraiseVideoModel> findEnablePraiseList(int followType);

}
