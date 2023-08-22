package com.example.follow.model.praise;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
@Table(name = "Praise")
public class PraiseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户id
     */

    private Long userId;
    /**
     * 点赞类型
     */

    private int praiseType;
    /**
     * 被点赞用户id
     */

    private long praisedUserId;

    /**
     * 视频id
     */
    private long videoId;

    /**
     * 早期关注
     */
    private boolean isEarlyPraise = false;

    @Column(name = "createTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;
}
