package com.example.follow.repository;

import com.example.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/15
 **/
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {





}
