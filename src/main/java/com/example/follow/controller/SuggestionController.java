package com.example.follow.controller;

import com.example.follow.model.Suggestion;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.SecurityUser;
import com.example.follow.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private SecurityUser securityUser;

    @PostMapping("/add")
    public Suggestion update(@RequestBody Suggestion suggestion) {
        suggestion.setUserId(securityUser.getUserId());
        return suggestionService.addSuggestion(suggestion);
    }
    @GetMapping("/list")
    public List<Suggestion> allSuggestion() {
        return suggestionService.getList();
    }
}
