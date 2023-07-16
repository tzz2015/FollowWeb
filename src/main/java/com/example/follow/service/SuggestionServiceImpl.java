package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.Suggestion;
import com.example.follow.repository.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
@Service
public class SuggestionServiceImpl implements SuggestionService {
    @Autowired
    private SuggestionRepository suggestionRepository;


    @Override
    public Suggestion addSuggestion(Suggestion suggestion) {
        try {
            return suggestionRepository.save(suggestion);
        } catch (Exception e) {
            throw new BusinessException("请修改建议后提交");
        }
    }
}
