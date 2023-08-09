package com.example.follow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author LYF
 * @dec: 脚本
 * @date 2023/8/9
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "script")
public class ScriptModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer version;
    private String decryptKey;
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false, updatable = false)
    private String scriptText;


}
