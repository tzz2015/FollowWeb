package com.example.follow.service;

import com.example.follow.model.AdSwitchModel;
import com.example.follow.repository.AdSwitchRepository;
import com.example.follow.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/12
 **/
@Service
public class AdSwitchServiceImpl implements AdSwitchService {
    @Autowired
    private AdSwitchRepository adSwitchRepository;

    @Override
    public AdSwitchModel unpdateAdSwitchModel(AdSwitchModel adSwitchModel) {
        if (TextUtil.isEmpty(adSwitchModel.getVersion())) {
            return null;
        }
        AdSwitchModel save;
        AdSwitchModel findAdSwitchModel = adSwitchRepository.findByVersion(adSwitchModel.getVersion());
        if (findAdSwitchModel == null) {
            save = adSwitchModel;
        } else {
            save = findAdSwitchModel;
            save.setStartSwitch(adSwitchModel.isStartSwitch());
            save.setBannerSwitch(adSwitchModel.isBannerSwitch());
            save.setFollowSwitchNum(adSwitchModel.getFollowSwitchNum());
        }
        return adSwitchRepository.save(save);
    }

    @Override
    public AdSwitchModel getAdSwitchModel(String version) {
        if (TextUtil.isEmpty(version)) {
            return null;
        }
        return adSwitchRepository.findByVersion(version);
    }
}
