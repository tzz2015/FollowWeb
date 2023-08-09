package com.example.follow.repository;

import com.example.follow.model.ScriptModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/9
 **/
@Repository
public interface ScriptRepository extends JpaRepository<ScriptModel, Long> {
}
