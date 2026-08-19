package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.model.Like;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private Long id;
    private Integer isLike;
    private String name;
    private Long articleId;
    private Long userId;
    private String createdDate;

    private String lastModifyDate;

    public LikeDto(Like like){
        this.id = like.getId();
        this.isLike = like.getIsLike();
        this.name = like.getUser().getName();
        this.articleId = like.getArticle().getId();
        this.userId = like.getUser().getId();
        this.createdDate = like.getCreatedAt().toString();
        this.lastModifyDate = like.getUpdatedAt().toString();
    }
}
