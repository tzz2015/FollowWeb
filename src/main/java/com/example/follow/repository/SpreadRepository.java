package com.example.follow.repository;

import com.example.follow.model.Spread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
@Repository
public interface SpreadRepository extends JpaRepository<Spread, Long> {

    Spread findFirstById(long id);
}
