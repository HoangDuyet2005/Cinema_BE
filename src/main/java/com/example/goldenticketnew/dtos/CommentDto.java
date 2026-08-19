package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String description;
    private String name;
    private String image;

    private String username;
    private Long articleId;
    private Long userId;
    private String createdAt;

    private String updatedAt;

    private String createdBy;
    private String modifiedBy;

    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.username = comment.getUser().getUsername();
        this.name = comment.getUser().getName();
        if(comment.getUser().getImage()!=null) this.image = comment.getUser().getImage();
        this.articleId = comment.getArticle().getId();
        this.userId = comment.getUser().getId();
        if(comment.getCreatedAt()!=null) this.createdAt = comment.getCreatedAt().toString();
        if(comment.getModifiedBy()!=null) this.modifiedBy = comment.getModifiedBy();
        if(comment.getCreatedBy()!=null) this.createdBy = comment.getCreatedBy();
        if(comment.getUpdatedAt()!=null) this.updatedAt = comment.getUpdatedAt().toString();
    }
}
