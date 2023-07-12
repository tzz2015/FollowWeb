package com.example.follow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "user_follow")
public class UserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(unique = true)
    private String account;

    private int followType;

    private int needFollowedCount;

    private int followedCount;

    private int followCount;

    @Column(name = "createTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;
}
