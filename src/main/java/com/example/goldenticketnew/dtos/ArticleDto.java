package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.enums.ArticleStatus;
import com.example.goldenticketnew.enums.ArticleType;
import com.example.goldenticketnew.model.Article;
import com.example.goldenticketnew.repository.ICommentRepository;
import com.example.goldenticketnew.repository.ILikeRepository;
import com.example.goldenticketnew.utils.BeanUtils;
import com.example.goldenticketnew.utils.ModelMapperUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto extends Auditable  {
    private Long id;
    private String title;
    private String brief;

    private ArticleStatus status;
    private String mainImage;

    private String description;

    private ArticleType type;

    private String keyword;

    private String slug;


    private String thumbnail;

    private Long totalLike;

    private Long totalComment;
    private Long view;


    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.brief = article.getBrief();
        this.status = article.getStatus();
        this.type = article.getType();
        this.mainImage = article.getMainImage();
        if(article.getThumbnail()!=null) this.thumbnail = article.getThumbnail();
        if(article.getKeyword() != null) this.keyword = article.getKeyword();
        if(article.getDescription()!=null) this.description = article.getDescription();
        if(article.getCreatedBy() != null) {
            this.setCreatedBy(article.getCreatedBy());
//            UserService userService = BeanUtils.getBean(UserService.class);
//            this.user = userService.getUserProfile(article.getCreatedBy());
        }
        if(article.getCreatedAt() != null) this.setCreatedAt(article.getCreatedAt().toString());
        if(article.getUpdatedAt() != null) this.setUpdatedAt(article.getUpdatedAt().toString());
        this.slug = ModelMapperUtils.removeAccentsWithApacheCommons(article.getTitle()+"-p"+article.getId());
        ILikeRepository likeRepository = BeanUtils.getBean(ILikeRepository.class);
        this.totalLike = likeRepository.countAllByArticleIdAndIsLike(article.getId());
        ICommentRepository commentRepository = BeanUtils.getBean(ICommentRepository.class);
        this.totalComment = commentRepository.countAllByArticleId(article.getId());
        this.view = article.getView();
    }
}
