package com.example.follow.repository.praise;

import com.example.follow.model.praise.PraiseModel;
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
public interface PraiseRepository extends JpaRepository<PraiseModel, Long>, JpaSpecificationExecutor<PraiseModel> {

    List<PraiseModel> findByUserIdAndPraiseType(long userId, int praiseType);

    List<PraiseModel> findByVideoId(long videoId);
}
