package com.example.follow.controller.praise;

import com.example.follow.model.praise.PraiseAccountModel;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.praise.PraiseAccountServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/22
 **/
@RestController
@RequestMapping("/api/praiseAccount")
@ResponseResult
public class PraiseAccountController {
    @Autowired
    private PraiseAccountServer praiseAccountServer;

    @GetMapping("/account/{followType}")
    public PraiseAccountModel getPraiseAccount(@PathVariable("followType") int followType) {
        return praiseAccountServer.getPraiseAccount(followType);
    }

    @PostMapping("/praiseCheck")
    public boolean checkIsCanPraise(@RequestBody PraiseAccountModel praise) {
        return praiseAccountServer.isCanPraised(praise.getUserId(), praise.getFollowType());
    }
}
