package com.example.goldenticketnew.model;

import com.example.goldenticketnew.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldNameConstants;


import javax.persistence.*;

@Getter
@Entity
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "like_user")
@FieldNameConstants
public class Like extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int isLike;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;
}
