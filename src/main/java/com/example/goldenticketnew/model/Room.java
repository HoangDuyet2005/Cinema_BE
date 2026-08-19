package com.example.goldenticketnew.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Table(name = "room")
@Entity
@NoArgsConstructor
@FieldNameConstants
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int capacity;
    private double totalArea;

    private String imgURL;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false,name = "branch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;
}
