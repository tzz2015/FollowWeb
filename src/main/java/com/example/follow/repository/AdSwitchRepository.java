package com.example.follow.repository;

import com.example.follow.model.AdSwitchModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
@Repository
public interface AdSwitchRepository extends JpaRepository<AdSwitchModel, Long> {



}

