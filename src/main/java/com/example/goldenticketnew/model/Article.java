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



}
