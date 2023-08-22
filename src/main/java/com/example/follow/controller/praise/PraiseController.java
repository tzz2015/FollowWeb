package com.example.follow.controller.praise;

import com.example.follow.model.praise.PraiseModel;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.praise.PraiseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/23
 **/
@RestController
@RequestMapping("/api/praise")
@ResponseResult
public class PraiseController {
    @Autowired
    private PraiseServer praiseServer;


    @PostMapping("/add")
    public PraiseModel update(@RequestBody PraiseModel praiseModel) {
        return praiseServer.addPraise(praiseModel);
    }

    @PostMapping("/earlyAdd")
    public PraiseModel addEarlyFollow(@RequestBody PraiseModel praiseModel) {
        return praiseServer.addEarlyPraise(praiseModel);
    }

    @GetMapping("/count")
    public long totalCount() {
        return praiseServer.totalCount();
    }
}
