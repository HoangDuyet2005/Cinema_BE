package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> , JpaSpecificationExecutor<Ticket> {
    List<Ticket> findTicketsBySchedule_Id(Integer scheduleId);
    List<Ticket> findTicketsBySchedule_IdAndSeat_Id(Integer scheduleId,Integer seatId);
    Ticket findTicketByAndSchedule_IdAndSeat_Id(Integer scheduleId,Integer seatId);
    List<Ticket> findTicketsByBillId(Integer billId);
    @Query(value = "SELECT * FROM ticket  WHERE ticket.bill_id IN (SELECT bill.id FROM bill where user_id = :userId) ORDER BY ticket.id DESC",nativeQuery = true)
    List<Ticket> findTicketsByUserId(@Param("userId") Long userId);
}