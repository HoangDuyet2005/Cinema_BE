package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.model.Ticket;
import com.example.goldenticketnew.utils.ModelMapperUtils;
import lombok.Data;

@Data
public class TicketDto {
    private int id;
    private String qrImageURL;
    private ScheduleDto schedule;
    private SeatDto seat;
    private BillDto bill;

    public TicketDto(Ticket ticket) {
        this.id = ticket.getId();
        this.qrImageURL = ticket.getQrImageURL();
        if(ticket.getSchedule()!=null) this.schedule = new ScheduleDto(ticket.getSchedule());
        if(ticket.getSeat()!=null )this.seat = new SeatDto(ticket.getSeat());
        if(ticket.getBill() != null)this.bill = new BillDto(ticket.getBill());
    }
}
