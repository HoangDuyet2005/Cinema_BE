package com.example.goldenticketnew.service.ticket;



import com.example.goldenticketnew.dtos.TicketDto;
import com.example.goldenticketnew.model.Ticket;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.repository.TicketRepository;
import com.example.goldenticketnew.utils.ModelMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService implements ITicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<TicketDto> getTicketsByUserId(Long userId) {
        try{
            return ticketRepository.findTicketsByUserId(userId).stream().map(TicketDto::new).collect(Collectors.toList());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    @Override
    public List<TicketDto> getAllTicketList() {
        return ticketRepository.findAll().stream().map(TicketDto::new).collect(Collectors.toList());

    }

    @Override
    public PageResponse<TicketDto> getAllTicketFilter(Pageable pageable) {
        try {
            Page<Ticket> page = ticketRepository.findAll(pageable);
            return new PageResponse<>(page.map(TicketDto::new));
        }catch (Exception e){
            System.out.println(e.getMessage());;
        }
return new PageResponse<>();

    }
}