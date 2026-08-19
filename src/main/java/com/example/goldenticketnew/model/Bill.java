package com.example.goldenticketnew.model;

import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.model.audit.UserDateAudit;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bill")
@NoArgsConstructor
@FieldNameConstants
public class Bill extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CreatedDate
    private LocalDateTime createdTime;
    @ManyToOne
    @JoinColumn(nullable = false,name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    private BillStatus status;
    private Double price;

}
