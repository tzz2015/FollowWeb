package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.ScriptModel;
import com.example.follow.repository.ScriptRepository;
import com.example.follow.utils.SymmetricEncryptionUtil;
import com.example.follow.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/9
 **/
@Service
public class ScriptServiceImpl implements ScriptService {
    @Autowired
    private ScriptRepository scriptRepository;

    /**
     * 创建或者更新
     *
     * @param scriptModel
     */
    @Override
    public ScriptModel createModel(ScriptModel scriptModel) {
        ScriptModel saveModel;
        if (TextUtil.isEmpty(scriptModel.getScriptText())) {
            throw new BusinessException("脚本不能为空");
        }
        saveModel = scriptRepository.findByFollowType(scriptModel.getFollowType());
        if (saveModel != null) {
            saveModel.setVersion(scriptModel.getVersion());
            saveModel.setScriptText(scriptModel.getScriptText());
        } else {
            saveModel = scriptModel;
        }
        return scriptRepository.save(saveModel);
    }

    @Override
    public ScriptModel findModel(int version, int followType, boolean isDebug) {
        ScriptModel scriptModel = scriptRepository.findByFollowType(followType);
        if (scriptModel == null) {
            return null;
        }
        if (!isDebug && scriptModel.getVersion() != null && version <= scriptModel.getVersion()) {
            return null;
        }
        String scripText = scriptModel.getScriptText();
        try {
            String[] encrypt = SymmetricEncryptionUtil.generateRandomEncrypt(scripText);
            scriptModel.setDecryptKey(encrypt[0]);
            scriptModel.setScriptText(encrypt[1]);
        } catch (Exception e) {
            throw new BusinessException("加密出错");
        }
        return scriptModel;
    }
}
