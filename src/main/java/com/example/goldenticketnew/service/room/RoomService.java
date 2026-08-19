package com.example.goldenticketnew.service.room;


import com.example.goldenticketnew.dtos.RoomDto;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.repository.IRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IRoomRepository IRoomRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RoomDto> getRooms(Integer movieId, Integer branchId, String startDate, String startTime) {
        return IRoomRepository.getRoomByBranchAndMovieAndSchedule(movieId,branchId, LocalDate.parse(startDate), LocalTime.parse(startTime))
                .stream().map(RoomDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDto> getListRoom(Integer branchId) {
        return IRoomRepository.findAllByBranchId(branchId)
            .stream().map(RoomDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public PageResponse<RoomDto> getPageRoom() {
        return null;
    }


}
