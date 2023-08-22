package com.example.follow.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    @Column(unique = true, nullable = false)
    private String phone;
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String roles;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "createTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;


}

