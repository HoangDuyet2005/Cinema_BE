package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ICommentRepository extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {
   Comment findFirstByUserIdAndArticleId(Long userId, Long articleId);
    Page<Comment> findAllByArticleId(Long articleId, Pageable pageable);

    Long countAllByArticleId(Long articleId);

    @Modifying
    @Query(value = "delete from comment where id = ?1",nativeQuery = true)
    void deleteByCommentId(Long id);
}
