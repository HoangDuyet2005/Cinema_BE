package com.example.goldenticketnew.service.seat;

import com.example.goldenticketnew.dtos.SeatDto;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Room;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.model.Seat;
import com.example.goldenticketnew.model.Ticket;
import com.example.goldenticketnew.repository.IBillRepository;
import com.example.goldenticketnew.repository.IScheduleRepository;
import com.example.goldenticketnew.repository.ISeatRepository;
import com.example.goldenticketnew.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SeatService implements ISeatService {
    @Autowired
    private ISeatRepository ISeatRepository;
    @Autowired
    private IScheduleRepository IScheduleRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private SeatRealtimeManager seatRealtimeManager;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<SeatDto> getSeatsByScheduleId(Integer scheduleId) {
        Schedule schedule = IScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new InternalException(ResponseCode.SCHEDULE_NOT_FOUND));
        Room room = schedule.getRoom();
        List<Seat> listSeat = ISeatRepository.getSeatByRoom_Id(room.getId());

        List<Ticket> allTickets = ticketRepository.findTicketsBySchedule_Id(scheduleId);

        // 1. CHỈ NHỮNG VÉ ĐÃ THANH TOÁN THÀNH CÔNG (status = SUCCESS) MỚI LÀ ĐÃ ĐẶT (isOccupied = 1)
        List<Integer> boughtSeatIds = allTickets.stream()
                .filter(t -> t != null && t.getBill() != null && t.getBill().getStatus() == BillStatus.SUCCESS)
                .map(t -> t.getSeat() != null ? t.getSeat().getId() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 2. Ghế ĐANG KHÓA REALTIME (do client đang kết nối và giữ chỗ qua WebSocket/API) -> isOccupied = 2
        List<Integer> holdingSeatIds = seatRealtimeManager.getHoldingSeatIds(scheduleId);

        return listSeat.stream().map(seat -> {
            SeatDto seatDTO = modelMapper.map(seat, SeatDto.class);
            if (boughtSeatIds.contains(seat.getId())) {
                seatDTO.setIsOccupied(1); // 1: Đã mua (Đã đặt) - Hiển thị ✕
            } else if (holdingSeatIds.contains(seat.getId())) {
                seatDTO.setIsOccupied(2); // 2: Đang khóa (Đang giữ chỗ) - Hiển thị 🔒
            } else {
                seatDTO.setIsOccupied(0); // 0: Ghế trống - Bất kỳ ai cũng chọn được ngay
            }
            return seatDTO;
        }).collect(Collectors.toList());
    }
}