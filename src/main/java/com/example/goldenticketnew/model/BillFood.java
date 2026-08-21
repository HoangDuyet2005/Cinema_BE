package com.example.goldenticketnew.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "bill_food")
@NoArgsConstructor
@AllArgsConstructor
public class BillFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FoodItem foodItem;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(nullable = false)
    private Double price;
}
