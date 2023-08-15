package com.example.follow.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author LYF
 * @dec: 推广
 * @date 2023/7/16
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "Spread")
public class Spread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false, unique = true)
    private String url;
    /**
     * 权重
     */
    private int weight = 0;
}
