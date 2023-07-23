package com.example.follow.controller;

import com.example.follow.model.Spread;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.model.response.ResultCode;
import com.example.follow.service.SpreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LYF
 * @dec: 推广
 * @date 2023/7/16
 **/
@RestController
@RequestMapping("/api/spread")
@ResponseResult
public class SpreadController {
    @Autowired
    private SpreadService spreadService;

    @PostMapping("/update")
    public Spread update(@RequestBody Spread spread) {
        return spreadService.updateSpread(spread);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        spreadService.deleteSpread(id);
        return ResultCode.SUCCESS.getMessage();
    }

    @GetMapping("/list")
    public List<Spread> findAll() {
        return spreadService.findAll();
    }

}
