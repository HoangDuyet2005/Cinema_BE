package com.example.goldenticketnew.model;

import com.example.goldenticketnew.enums.ArticleStatus;
import com.example.goldenticketnew.enums.ArticleType;
import com.example.goldenticketnew.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "article")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Article extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "title")
    private String title;

    @Column(name = "brief")
    private String brief;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private ArticleStatus status;

    @Column(name = "type")
    private ArticleType type;

    private String keyword;

    private String thumbnail;

    private long view = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMainImage() { return mainImage; }
    public void setMainImage(String mainImage) { this.mainImage = mainImage; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBrief() { return brief; }
    public void setBrief(String brief) { this.brief = brief; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ArticleStatus getStatus() { return status; }
    public void setStatus(ArticleStatus status) { this.status = status; }
    public ArticleType getType() { return type; }
    public void setType(ArticleType type) { this.type = type; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public long getView() { return view; }
    public void setView(long view) { this.view = view; }
}