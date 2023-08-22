package com.example.follow.service.praise;

import com.example.follow.except.BusinessException;
import com.example.follow.model.praise.PraiseAccountModel;
import com.example.follow.model.user.User;
import com.example.follow.repository.praise.PraiseAccountRepository;
import com.example.follow.service.SecurityUser;
import com.example.follow.service.UserService;
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
public class PraiseAccountServerImpl implements PraiseAccountServer {
    @Autowired
    private PraiseAccountRepository repository;
    @Autowired
    private SecurityUser securityUser;

    @Autowired
    private UserService userService;

    /**
     * 获取点赞账户信息
     *
     * @param followType
     * @return
     */
    @Override
    public PraiseAccountModel getPraiseAccount(int followType) {
        return getPraiseAccount(securityUser.getUserId(), followType);
    }

    /**
     * 获取点赞账户信息
     *
     * @param userId
     * @param followType
     * @return
     */
    @Override
    public PraiseAccountModel getPraiseAccount(long userId, int followType) {
        PraiseAccountModel model = repository.findByUserIdAndFollowType(userId, followType);
        if (model == null) {
            User user = userService.findUserById(userId);
            if (user == null) {
                throw new BusinessException("用户不存在");
            }
            model = new PraiseAccountModel();
            model.setUserId(userId);
            model.setNeedPraiseCount(5);
            model.setFollowType(followType);
            repository.save(model);
        }
        return model;
    }

    /**
     * 增加点赞数量
     *
     * @param userId
     * @param followType
     * @return
     */
    @Override
    public PraiseAccountModel addPraiseCount(long userId, int followType) {
        PraiseAccountModel praiseAccount = getPraiseAccount(userId, followType);
        praiseAccount.setPraiseCount(praiseAccount.getPraiseCount() + 1);
        praiseAccount.setNeedPraiseCount(praiseAccount.getNeedPraiseCount() + 1);
        return praiseAccount;
    }

    /**
     * 增加被点赞
     *
     * @param userId
     * @param followType
     * @return
     */
    @Override
    public PraiseAccountModel addPraisedCount(long userId, int followType) {
        if (isCanPraised(userId, followType)) {
            PraiseAccountModel praiseAccount = getPraiseAccount(userId, followType);
            praiseAccount.setPraisedCount(praiseAccount.getPraisedCount() + 1);
            praiseAccount.setNeedPraiseCount(praiseAccount.getNeedPraiseCount() - 1);
            return praiseAccount;
        }
        throw new BusinessException("点前用户不可点赞");
    }

    /**
     * 是否可以被点赞
     *
     * @param userId
     * @param followType
     * @return
     */
    @Override
    public boolean isCanPraised(long userId, int followType) {
        PraiseAccountModel praiseAccount = getPraiseAccount(userId, followType);
        return praiseAccount.getNeedPraiseCount() > 0;
    }

    /**
     * 查找可点赞的列表
     *
     * @param followType
     * @return
     */
    @Override
    public List<PraiseAccountModel> findEnablePraiseList(int followType) {
        Specification<PraiseAccountModel> specification = (Root<PraiseAccountModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate followTypePredicate = criteriaBuilder.equal(root.get("followType"), followType);
            Predicate userPredicate = criteriaBuilder.notEqual(root.get("userId"), securityUser.getUserId());
            Predicate followedCountPredicate = criteriaBuilder.greaterThan(root.get("needFollowedCount"), 0);
            Predicate accountNotInPredicate = criteriaBuilder.conjunction();
            Predicate predicate = criteriaBuilder.and(followTypePredicate, userPredicate, followedCountPredicate, accountNotInPredicate);
            query.orderBy(criteriaBuilder.asc(root.get("needFollowedCount")), criteriaBuilder.desc(root.get("createTime")));
            return predicate;
        };
        return repository.findAll(specification, PageRequest.of(0, 30)).getContent();
    }
}
