package com.noyu.test_app;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    // 内容はJpaRepositoryを継承することで自動的に追加される（便利！）

    // emailでUserを検索
    // 検索結果がない場合にnullではなくOptional.empty()を返す
    Optional<User> findByEmail(String email);
}
