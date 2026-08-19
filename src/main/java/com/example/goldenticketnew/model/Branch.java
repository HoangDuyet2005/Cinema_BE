package com.example.goldenticketnew.model;

import com.example.goldenticketnew.model.audit.DateAudit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Data
@Table(name = "branch")
@Entity
@NoArgsConstructor
@FieldNameConstants
public class Branch extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 2000)
    private String imgURL;
    private String name;
    private String address;
    private String phoneNo;
}
