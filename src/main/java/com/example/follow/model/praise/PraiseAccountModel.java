package com.example.follow.model.praise;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author LYF
 * @dec: 点赞统计类
 * @date 2023/8/22
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "PraiseAccount")
public class PraiseAccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    private int followType = 0;
    /**
     * 剩余点赞数
     */
    private int needPraiseCount = 0;
    /**
     * 被点赞数量
     */
    private int praisedCount = 0;
    /**
     * 点赞数量
     */
    private int praiseCount = 0;

    @Column(name = "createTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

}
