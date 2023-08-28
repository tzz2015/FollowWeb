package com.example.follow.service.praise;

import com.example.follow.except.BusinessException;
import com.example.follow.model.praise.PraiseModel;
import com.example.follow.repository.praise.PraiseRepository;
import com.example.follow.service.SecurityUser;
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
 * @date 2023/8/23
 **/
@Service
public class PraiseServerImpl implements PraiseServer {

    @Autowired
    private PraiseRepository repository;
    @Autowired
    private SecurityUser securityUser;
    @Autowired
    private PraiseAccountServer praiseAccountServer;

    /**
     * 添加记录
     *
     * @param praiseModel
     * @return
     */
    @Transactional
    @Override
    public PraiseModel addPraise(PraiseModel praiseModel) {
        praiseModel.setUserId(securityUser.getUserId());
        PraiseModel findPraise = find(praiseModel);
        if (findPraise != null) {
            return findPraise;
        }
        try {
            PraiseModel save = repository.save(praiseModel);
            praiseAccountServer.addPraiseCount(save.getUserId(), praiseModel.getPraiseType());
            praiseAccountServer.addPraisedCount(save.getPraisedUserId(), praiseModel.getPraiseType());
            return save;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 早期点赞的记录
     *
     * @param praiseModel
     * @return
     */
    @Transactional
    @Override
    public PraiseModel addEarlyPraise(PraiseModel praiseModel) {
        praiseModel.setEarlyPraise(true);
        praiseModel.setUserId(securityUser.getUserId());
        PraiseModel findPraise = find(praiseModel);
        if (findPraise != null) {
            return praiseModel;
        }
        return repository.save(praiseModel);
    }

    /**
     * 查询记录
     *
     * @param praiseModel
     * @return
     */
    @Override
    public PraiseModel find(PraiseModel praiseModel) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                // 字符串匹配方式
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                // 忽略大小写
                .withIgnoreCase()
                .withIgnoreNullValues();
        Example<PraiseModel> example = Example.of(praiseModel, matcher);
        List<PraiseModel> list = repository.findAll(example);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 累计点赞
     *
     * @return
     */
    @Override
    public long totalCount() {
        return repository.findAll().size();
    }

    /**
     * 视频被点赞的数量
     *
     * @param videoId
     * @return
     */
    @Override
    public int videoPraiseCount(long videoId) {
        List<PraiseModel> videoList = repository.findByVideoId(videoId);
        return videoList != null ? videoList.size() : 0;
    }

    /**
     * 获取当前点赞过的列表id
     *
     * @param praiseType
     * @return
     */
    @Override
    public List<Long> getUserPraiseAccount(int praiseType) {
        List<Long> userList = new ArrayList<>();
        List<PraiseModel> followList = repository.findByUserIdAndPraiseType(securityUser.getUserId(), praiseType);
        for (PraiseModel follow : followList) {
            userList.add(follow.getVideoId());
        }
        return userList;
    }
}
