package com.example.follow.repository;

import com.example.follow.model.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/13
 **/
@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    UserFollow findByFollowTypeAndUserId(int type, long userId);

    List<UserFollow> findAllByUserId(String userId);
}
