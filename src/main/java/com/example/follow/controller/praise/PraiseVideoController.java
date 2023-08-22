package com.example.follow.controller.praise;

import com.example.follow.model.praise.PraiseVideoModel;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.praise.PraiseVideoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/22
 **/
@RestController
@RequestMapping("/api/praiseVideo")
@ResponseResult
public class PraiseVideoController {
    @Autowired
    private PraiseVideoServer praiseVideoServer;

    @DeleteMapping("/delete/{id}")
    public boolean deletePraise(@PathVariable("id") int id) {
        return praiseVideoServer.deletePraise(id);
    }

    @PostMapping("/update")
    public PraiseVideoModel createOrUpdate(@RequestBody PraiseVideoModel praise) {
        return praiseVideoServer.saveOrUpdate(praise);
    }

    @GetMapping("/praiseList/{followType}")
    public List<PraiseVideoModel> getPraiseList(@PathVariable("followType") int followType) {
        return praiseVideoServer.findPraiseList(followType);
    }

    @GetMapping("/enablePraiseList/{followType}")
    public List<PraiseVideoModel> getEnablePraiseList(@PathVariable("followType") int followType) {
        return praiseVideoServer.findEnablePraiseList(followType);
    }


}
