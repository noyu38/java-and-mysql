package com.noyu.test_app;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 内容はJpaRepositoryを継承することで自動的に追加される（便利！）
}
