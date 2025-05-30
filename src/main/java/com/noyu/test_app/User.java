package com.noyu.test_app;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // 対応するテーブル名を指定する
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDが自動生成されることを意味する
    private Long id;

    @Column(nullable = false) // NULLを許容しない
    private String name;

    @Column(nullable = false, unique = true) // NULLを許容しない & 一意
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public User() {
        this.createdAt = LocalDateTime.now(); // 現在時刻を設定
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name=" + name + '\'' +
               ", email=" + email + '\'' +
               ", createdAt=" + createdAt +
               "}";
    }
}
