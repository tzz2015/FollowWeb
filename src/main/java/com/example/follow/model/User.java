package com.example.follow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String phone;
    private String username;
    @Column(nullable = false)
    private String password;

    private String email;
    @Column(name = "createTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;


}

