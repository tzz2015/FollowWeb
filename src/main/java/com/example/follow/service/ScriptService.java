package com.example.follow.service;

import com.example.follow.model.ScriptModel;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/9
 **/
public interface ScriptService {
    /**
     * 创建或者更新
     */
    ScriptModel createModel(ScriptModel scriptModel);

    ScriptModel findModel(int version,boolean isDebug);

}
