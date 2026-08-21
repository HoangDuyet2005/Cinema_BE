package com.example.goldenticketnew.service.seat;

import com.example.goldenticketnew.dtos.SeatDto;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Room;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.model.Seat;
import com.example.goldenticketnew.model.Ticket;
import com.example.goldenticketnew.payload.seat.ConfigureRoomSeatsRequest;
import com.example.goldenticketnew.repository.IScheduleRepository;
import com.example.goldenticketnew.repository.ISeatRepository;
import com.example.goldenticketnew.repository.TicketRepository;
import com.example.goldenticketnew.service.pricing.PriceCalculationService;
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
    private SeatRealtimeManager seatRealtimeManager;
    @Autowired
    private PriceCalculationService priceCalculationService;
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
            seatDTO.setType(seat.getSeatType());
            // Tính toán giá vé thực tế cho từng loại ghế của suất chiếu này
            double seatPrice = priceCalculationService.calculateSeatPrice(schedule, seat);
            seatDTO.setPrice(seatPrice);

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

    @Override
    @Transactional(readOnly = true)
    public List<SeatDto> getSeatsByRoomId(Integer roomId) {
        List<Seat> listSeat = ISeatRepository.getSeatByRoom_Id(roomId);
        return listSeat.stream().map(seat -> {
            SeatDto dto = modelMapper.map(seat, SeatDto.class);
            dto.setType(seat.getSeatType());
            dto.setIsOccupied(0);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void configureRoomSeats(ConfigureRoomSeatsRequest request) {
        if (request == null || request.getRoomId() == null || request.getSeats() == null) {
            throw new InternalException(ResponseCode.INVALID_PARAM);
        }

        Room room = new Room();
        room.setId(request.getRoomId());

        // 1. Xóa ghế cũ của phòng
        List<Seat> oldSeats = ISeatRepository.getSeatByRoom_Id(request.getRoomId());
        ISeatRepository.deleteAll(oldSeats);

        // 2. Thêm danh sách ghế mới
        List<Seat> newSeats = request.getSeats().stream().map(item -> {
            Seat s = new Seat();
            s.setName(item.getName());
            int typeVal = item.getSeatType() != null ? item.getSeatType() : 0;
            switch (typeVal) {
                case 1: s.setSeatType(com.example.goldenticketnew.enums.SeatType.VIP); break;
                case 2: s.setSeatType(com.example.goldenticketnew.enums.SeatType.COUPLE); break;
                case 3: s.setSeatType(com.example.goldenticketnew.enums.SeatType.TRIPLE); break;
                case 0:
                default: s.setSeatType(com.example.goldenticketnew.enums.SeatType.NORMAL); break;
            }
            s.setRoom(room);
            return s;
        }).collect(Collectors.toList());

        ISeatRepository.saveAll(newSeats);
    }
}