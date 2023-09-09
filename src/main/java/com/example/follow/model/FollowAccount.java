package com.example.follow.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author LYF
 * @dec: 抖音号小红书号表
 * @date 2023/7/12
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "follow_account")
public class FollowAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String account;

    private int followType;

    private int needFollowedCount;

    private int followedCount;

    private int followCount;

    private int payFollowCount;

    @Column(name = "createTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;
}
