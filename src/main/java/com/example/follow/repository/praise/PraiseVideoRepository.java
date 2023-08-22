package com.example.follow.repository.praise;

import com.example.follow.model.praise.PraiseVideoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/22
 **/
@Repository
public interface PraiseVideoRepository extends JpaRepository<PraiseVideoModel, Long>, JpaSpecificationExecutor<PraiseVideoModel> {

    PraiseVideoModel findByIdAndUserId(long id, long userId);

    List<PraiseVideoModel> findByUserIdAndFollowType(long userId, int followType);
}
