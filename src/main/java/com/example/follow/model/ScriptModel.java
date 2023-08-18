package com.example.follow.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private Integer followType;
    private Integer version;
    private String decryptKey;
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String scriptText;


}
