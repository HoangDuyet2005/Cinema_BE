package com.example.goldenticketnew.repository;


import com.example.goldenticketnew.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILikeRepository extends JpaRepository<Like,Long>, JpaSpecificationExecutor<Like> {
    Like findFirstByUserIdAndArticleId(Long userId, Long articleId);

    @Query(value = "select * from like_user where user_id = ?1 and article_id = ?2  and is_like = 1",nativeQuery = true)
    Like findByUserIdAndArticleIdAndIsLike(Long userId, Long articleId);
    @Query(value = "select * from like_user where article_id = ?1 and is_like = 1",nativeQuery = true)
    List<Like> findAllByArticleId(Long articleId);
    @Query(value = "select count(id) from like_user where article_id = ?1 and is_like = 1",nativeQuery = true)
    Long countAllByArticleIdAndIsLike(Long articleId);
}
