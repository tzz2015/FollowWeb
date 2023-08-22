package com.example.follow.model.praise;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/22
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "PraiseVideo")
public class PraiseVideoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int followType;
    private String title;
    private String url;
    private int count = 0;
    @Column(name = "createTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;
}
