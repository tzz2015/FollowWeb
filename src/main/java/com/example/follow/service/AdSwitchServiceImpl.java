package com.example.follow.service;

import com.example.follow.model.AdSwitchModel;
import com.example.follow.repository.AdSwitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        AdSwitchModel save;
        List<AdSwitchModel> list = adSwitchRepository.findAll();
        if (list.isEmpty()) {
            save = adSwitchModel;
        } else {
            save = list.get(0);
            save.setStartSwitch(adSwitchModel.isStartSwitch());
            save.setBannerSwitch(adSwitchModel.isBannerSwitch());
            save.setFollowSwitch(adSwitchModel.isFollowSwitch());
        }
        return adSwitchRepository.save(save);
    }
}
