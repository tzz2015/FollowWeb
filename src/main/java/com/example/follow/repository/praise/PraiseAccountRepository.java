package com.example.follow.repository.praise;

import com.example.follow.model.praise.PraiseAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/22
 **/
@Repository
public interface PraiseAccountRepository extends JpaRepository<PraiseAccountModel, Long>, JpaSpecificationExecutor<PraiseAccountModel> {

    PraiseAccountModel findByUserIdAndFollowType(long userId, int followType);
}
