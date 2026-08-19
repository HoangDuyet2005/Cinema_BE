package com.example.goldenticketnew.model;

import com.example.goldenticketnew.model.audit.UserDateAudit;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Table(name = "ticket")
@Entity
public class Ticket extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String qrImageURL;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false,name = "seat_id")
    private Seat seat;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false,name = "schedule_id")
    private Schedule schedule;
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="bill_id")
    private Bill bill;
}
