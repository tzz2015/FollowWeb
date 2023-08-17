package com.example.follow.service;

import com.example.follow.model.AdSwitchModel;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/12
 **/
public interface AdSwitchService {
    AdSwitchModel unpdateAdSwitchModel(AdSwitchModel adSwitchModel);

    AdSwitchModel getAdSwitchModel(String version);

}
