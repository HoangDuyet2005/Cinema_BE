package com.example.goldenticketnew.service.seat;

import com.example.goldenticketnew.dtos.SeatDto;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Room;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.model.Seat;
import com.example.goldenticketnew.repository.IScheduleRepository;
import com.example.goldenticketnew.repository.ISeatRepository;
import com.example.goldenticketnew.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService implements ISeatService{
    @Autowired
    private ISeatRepository ISeatRepository;
    @Autowired
    private IScheduleRepository IScheduleRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SeatDto> getSeatsByScheduleId(Integer scheduleId) {
        // Lấy ra các chỗ ngồi của phòng trong lịch đó
        Schedule schedule = IScheduleRepository.findById(scheduleId).orElseThrow(() -> new InternalException(ResponseCode.SCHEDULE_NOT_FOUND));
        Room room = IScheduleRepository.getById(scheduleId).getRoom();
        List<Seat> listSeat = ISeatRepository.getSeatByRoom_Id(room.getId());

        // Lấy ra các vé đã được đặt trong lịch đó rồi map sang các chỗ ngồi
        List<Seat> occupiedSeats = ticketRepository.findTicketsBySchedule_Id(scheduleId)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());

        // Map list chỗ ngồi của phòng ở bước 1 sang list dto
        List<SeatDto> filteredSeats = listSeat.stream().map(seat -> {
           SeatDto seatDTO = modelMapper.map(seat, SeatDto.class);
           if(occupiedSeats.stream()
                   .map(occupiedSeat->occupiedSeat.getId())
                   .collect(Collectors.toList()).contains(seat.getId())){
               seatDTO.setIsOccupied(1); // Nếu ghế nào nằm trong list ghế đã được occupied thì set = 1
           }
           return seatDTO;
        }).collect(Collectors.toList());

        return  filteredSeats;
    }
}
