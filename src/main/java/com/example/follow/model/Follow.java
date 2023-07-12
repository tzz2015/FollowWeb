package com.example.follow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * @author LYF
 * @dec: 关注表
 * @date 2023/7/12
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int followType;

    private long followUserId;

    @Column(unique = true)
    private String followAccount;

    @Column(name = "createTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;
}
