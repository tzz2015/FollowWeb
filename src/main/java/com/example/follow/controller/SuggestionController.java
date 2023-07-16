package com.example.follow.controller;

import com.example.follow.model.Suggestion;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
@RestController
@RequestMapping("/api/suggestion")
@ResponseResult
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @PostMapping("/add")
    public Suggestion update(@RequestBody Suggestion suggestion) {
        return suggestionService.addSuggestion(suggestion);
    }
}
