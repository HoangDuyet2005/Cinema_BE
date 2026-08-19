package com.example.goldenticketnew.service.ticket;

import com.example.goldenticketnew.dtos.TicketDto;
import com.example.goldenticketnew.payload.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITicketService {
    List<TicketDto> getTicketsByUserId(Long userId);
    List<TicketDto> getAllTicketList();

    PageResponse<TicketDto> getAllTicketFilter(Pageable pageable);
}
