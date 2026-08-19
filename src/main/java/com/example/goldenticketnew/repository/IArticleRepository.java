package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.enums.ArticleStatus;
import com.example.goldenticketnew.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IArticleRepository extends JpaRepository<Article,Long> , JpaSpecificationExecutor<Article> {
    @Query(value = " Select count(id) from article  where year(created_at) = ?1 and month(created_at) = ?2",nativeQuery = true)
    Long getTotalArticle(int year,int month);

    @Query(value = " Select count(id) from article  where year(created_at) = ?1 and month(created_at) = ?2 and created_by = ?3",nativeQuery = true)
    Long getTotalArticleInUser(int year,int month,String username);

}
