package com.example.follow.repository;

import com.example.follow.model.FollowAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/13
 **/
@Repository
public interface FollowAccountRepository extends JpaRepository<FollowAccount, Long>, JpaSpecificationExecutor<FollowAccount> {

    FollowAccount findByFollowTypeAndUserId(int type, long userId);

    List<FollowAccount> findAllByUserId(long userId);
}
