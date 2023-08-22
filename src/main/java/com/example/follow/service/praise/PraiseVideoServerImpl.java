package com.example.follow.service.praise;

import com.example.follow.except.BusinessException;
import com.example.follow.model.praise.PraiseVideoModel;
import com.example.follow.repository.praise.PraiseVideoRepository;
import com.example.follow.service.SecurityUser;
import com.example.follow.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/22
 **/
@Service
public class PraiseVideoServerImpl implements PraiseVideoServer {
    @Autowired
    private PraiseVideoRepository repository;
    @Autowired
    private SecurityUser securityUser;

    @Autowired
    private PraiseAccountServer accountServer;

    @Override
    public boolean deletePraise(long id) {
        PraiseVideoModel model = repository.findByIdAndUserId(id, securityUser.getUserId());
        if (model != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * 添加或者更新
     *
     * @param praiseVideoModel
     * @return
     */
    @Override
    public PraiseVideoModel saveOrUpdate(PraiseVideoModel praiseVideoModel) {
        if (TextUtil.isEmpty(praiseVideoModel.getTitle())) {
            throw new BusinessException("标题不能为空");
        }
        if (TextUtil.isEmpty(praiseVideoModel.getUrl())) {
            throw new BusinessException("连接不能为空");
        }
        if (praiseVideoModel.getId() == null) {
            praiseVideoModel.setId(0L);
        }
        PraiseVideoModel saveModel = repository.findByIdAndUserId(praiseVideoModel.getId(), securityUser.getUserId());
        if (saveModel != null) {
            saveModel.setTitle(praiseVideoModel.getTitle());
            saveModel.setUrl(praiseVideoModel.getUrl());
        } else {
            saveModel = praiseVideoModel;
            saveModel.setUserId(securityUser.getUserId());
        }
        return repository.save(saveModel);
    }

    /**
     * 获取视频列表
     *
     * @param followType
     * @return
     */
    @Override
    public List<PraiseVideoModel> findPraiseList(int followType) {
        List<PraiseVideoModel> videoList = repository.findByUserIdAndFollowType(securityUser.getUserId(), followType);
        // 查找数量
        return videoList;
    }

    /**
     * 查找可点赞的列表
     *
     * @param followType
     * @return
     */
    @Override
    public List<PraiseVideoModel> findEnablePraiseList(int followType) {
        List<Long> enablePraiseList = accountServer.findEnablePraiseList(followType);
        Specification<PraiseVideoModel> specification = (Root<PraiseVideoModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            // 当前类型
            Predicate followTypePredicate = criteriaBuilder.equal(root.get("followType"), followType);
            // 不包括自己
            Predicate userPredicate = criteriaBuilder.notEqual(root.get("userId"), securityUser.getUserId());
            // 在enablePraiseList里面
            Predicate userIdInPredicate = criteriaBuilder.conjunction();
            if (!enablePraiseList.isEmpty()) {
                userIdInPredicate = criteriaBuilder.and(root.get("userId").in(enablePraiseList));
            }
            // 不在点赞列表
//            Predicate accountNotInPredicate = criteriaBuilder.conjunction();
            Predicate predicate = criteriaBuilder.and(followTypePredicate, userPredicate, userIdInPredicate);
            // 排序 按创建时间从大道小
            query.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return predicate;
        };
        return repository.findAll(specification, PageRequest.of(0, 30)).getContent();
    }
}
