package com.example.follow.service;

import com.example.follow.model.Suggestion;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
public interface SuggestionService {
    Suggestion addSuggestion(Suggestion suggestion);

    List<Suggestion> getList();
}
