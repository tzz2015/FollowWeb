package com.example.follow.controller;

import com.example.follow.model.ScriptModel;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/9
 **/
@RestController
@RequestMapping("/api/script")
@ResponseResult
public class ScriptController {
    @Autowired
    private ScriptService scriptService;


    @PostMapping("/add")
    public ScriptModel update(@RequestParam("version") int version, @RequestParam("scriptText") String scriptText) {
        ScriptModel scriptModel = new ScriptModel();
        scriptModel.setVersion(version);
        scriptModel.setScriptText(scriptText);
        return scriptService.createModel(scriptModel);
    }

    @PostMapping("/find")
    public ScriptModel find(@RequestParam("version") int version, @RequestParam("isDebug") boolean isDebug) {
        return scriptService.findModel(version, isDebug);
    }


}
