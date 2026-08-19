package com.example.goldenticketnew.payload.article.request;

import com.example.goldenticketnew.enums.ArticleStatus;
import com.example.goldenticketnew.enums.ArticleType;
import com.example.goldenticketnew.model.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GetAllArticleRequest {
    @JsonIgnore
    private Pageable pageable;

    private String title;

    private ArticleStatus status;

    private String keyword;

    private String username;

    private ArticleType articleType;



    public Specification<Article> getSpecification(){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null) {
                predicates.add(cb.like(cb.lower(root.get(Article.Fields.title)), "%" + title.toLowerCase() + "%"));
            }
            if (keyword != null) {
                predicates.add(cb.like(cb.lower(root.get(Article.Fields.keyword)), "%" + keyword.toLowerCase() + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get(Article.Fields.status),status));
            }
            if(username != null){
                predicates.add(cb.equal(root.get("createdBy"),username));
            }
            if(articleType != null){
                predicates.add(cb.equal(root.get(Article.Fields.type),articleType));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
