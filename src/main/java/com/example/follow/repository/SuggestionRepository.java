package com.example.follow.repository;

import com.example.follow.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
@Repository
public interface SuggestionRepository  extends JpaRepository<Suggestion, Long> {
}
