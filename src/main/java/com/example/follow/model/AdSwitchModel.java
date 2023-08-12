package com.example.follow.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author LYF
 * @dec: 广告开关
 * @date 2023/7/16
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "AdSwitch")
public class AdSwitchModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 开屏页面广告开关
     */
    private boolean startSwitch;
    /**
     * banner
     */
    private boolean bannerSwitch;
    /**
     * 点击关注广告开关
     */
    private boolean followSwitch;


}
