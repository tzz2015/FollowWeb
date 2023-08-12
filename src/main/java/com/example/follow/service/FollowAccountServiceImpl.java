package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.FollowAccount;
import com.example.follow.model.response.ResultCode;
import com.example.follow.repository.FollowAccountRepository;
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
 * @date 2023/7/14
 **/
@Service
public class FollowAccountServiceImpl implements FollowAccountService {
    @Autowired
    private FollowAccountRepository followAccountRepository;
    @Autowired
    private SecurityUser securityUser;
    @Autowired
    private FollowService followService;

    @Override
    public FollowAccount createOrSave(int type, String account) {
        if (TextUtil.isEmpty(account)) {
            throw new BusinessException("账户不能为空");
        }
        FollowAccount followAccount = findByType(type, securityUser.getUserId());
        if (followAccount != null) {
            followAccount.setAccount(account);
        } else {
            followAccount = new FollowAccount();
            followAccount.setUserId(securityUser.getUserId());
            followAccount.setFollowType(type);
            followAccount.setAccount(account);
            // 默认加十个被关注
            followAccount.setNeedFollowedCount(10);
        }
        return followAccountRepository.save(followAccount);
    }

    @Override
    public List<FollowAccount> findAll() {
        return followAccountRepository.findAllByUserId(securityUser.getUserId());
    }

    @Override
    public FollowAccount saveByCount(FollowAccount followAccount) {
        FollowAccount follow = findByType(followAccount.getFollowType(), followAccount.getUserId());
        if (follow == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "不允许修改");
        }
        follow.setFollowCount(followAccount.getFollowCount());
        follow.setNeedFollowedCount(followAccount.getNeedFollowedCount());
        follow.setFollowedCount(followAccount.getFollowedCount());
        return followAccountRepository.save(follow);
    }

    @Override
    public FollowAccount findByType(int type, long userId) {
        return followAccountRepository.findByFollowTypeAndUserId(type, userId);
    }

    @Override
    public FollowAccount findByType(int type) {
        return findByType(type, securityUser.getUserId());
    }

    /**
     * 校验账户是否可以被关注
     *
     * @param type
     * @param userId
     * @return
     */
    @Override
    public FollowAccount checkIsCanFollow(int type, long userId) {
        FollowAccount followAccount = findByType(type, userId);
        if (followAccount != null && followAccount.getNeedFollowedCount() <= 0) {
            return null;
        }
        return followAccount;
    }

    /**
     * 查找可关注的列表
     *
     * @param followType
     * @return
     */
    @Override
    public List<FollowAccount> findEnableFollowList(int followType) {
        List<String> userFollowList = followService.getUserFollowAccount(followType);
        Specification<FollowAccount> specification = (Root<FollowAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate followTypePredicate = criteriaBuilder.equal(root.get("followType"), followType);
            Predicate userPredicate = criteriaBuilder.notEqual(root.get("userId"), securityUser.getUserId());
            Predicate followedCountPredicate = criteriaBuilder.greaterThan(root.get("needFollowedCount"), 0);
            Predicate accountNotInPredicate = criteriaBuilder.conjunction();
            if (!userFollowList.isEmpty()) {
                accountNotInPredicate = criteriaBuilder.not(root.get("account").in(userFollowList));
            }
            Predicate predicate = criteriaBuilder.and(followTypePredicate, userPredicate, followedCountPredicate, accountNotInPredicate);
            query.orderBy(criteriaBuilder.asc(root.get("needFollowedCount")), criteriaBuilder.desc(root.get("createTime")));
            return predicate;
        };
        return followAccountRepository.findAll(specification, PageRequest.of(0, 30)).getContent();
    }
}
