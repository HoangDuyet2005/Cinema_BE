package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.SeatDto;
import com.example.goldenticketnew.dtos.SeatRealtimeMessage;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.model.Seat;
import com.example.goldenticketnew.model.Ticket;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.repository.ISeatRepository;
import com.example.goldenticketnew.repository.TicketRepository;
import com.example.goldenticketnew.service.seat.ISeatService;
import com.example.goldenticketnew.service.seat.SeatRealtimeManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/seats")
@Tag(name = "Seat Controller", description = "Thao tác với Seat")
@RequiredArgsConstructor
@Slf4j
public class SeatController {

    private final ISeatService seatService;
    private final SeatRealtimeManager seatRealtimeManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final TicketRepository ticketRepository;
    private final ISeatRepository seatRepository;

    @GetMapping
    public ResponseEntity<ResponseBase<List<SeatDto>>> getSeatsByScheduleId(@RequestParam Integer scheduleId){
        return ResponseEntity.ok(new ResponseBase<>(seatService.getSeatsByScheduleId(scheduleId)));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ResponseBase<List<SeatDto>>> getSeatsByRoomId(@PathVariable Integer roomId){
        return ResponseEntity.ok(new ResponseBase<>(seatService.getSeatsByRoomId(roomId)));
    }

    @PostMapping("/configure-room")
    public ResponseEntity<ResponseBase<String>> configureRoomSeats(@RequestBody com.example.goldenticketnew.payload.seat.ConfigureRoomSeatsRequest request){
        seatService.configureRoomSeats(request);
        return ResponseEntity.ok(new ResponseBase<>("Cấu hình sơ đồ ghế thành công!"));
    }

    @Data
    public static class HoldSeatsRequest {
        private Integer scheduleId;
        private List<Integer> seatIds;
        private Integer userId;
    }

    @PostMapping("/hold-seats")
    public ResponseEntity<?> holdSeats(@RequestBody HoldSeatsRequest request) {
        if (request.getScheduleId() == null || request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Dữ liệu không hợp lệ"));
        }

        Integer userId = request.getUserId() != null ? request.getUserId() : 1;
        List<String> conflictSeatNames = new ArrayList<>();

        // 1. Kiểm tra từng ghế xem đã có ai mua hoặc giữ chưa
        for (Integer seatId : request.getSeatIds()) {
            Seat seat = seatRepository.findFirstById(seatId);
            String seatName = seat != null ? seat.getName() : String.valueOf(seatId);

            // Check đã mua thành công trong DB
            List<Ticket> tickets = ticketRepository.findTicketsBySchedule_IdAndSeat_Id(request.getScheduleId(), seatId);
            boolean isBought = tickets.stream().anyMatch(t -> t.getBill() != null && t.getBill().getStatus() == BillStatus.SUCCESS);
            if (isBought) {
                conflictSeatNames.add(seatName);
                continue;
            }

            // Check trong memory manager xem ai đang hold không
            Integer holdingUser = seatRealtimeManager.getHoldingUserId(request.getScheduleId(), seatId);
            if (holdingUser != null && !holdingUser.equals(userId)) {
                conflictSeatNames.add(seatName);
            }
        }

        if (!conflictSeatNames.isEmpty()) {
            return ResponseEntity.status(409).body(Map.of(
                    "success", false,
                    "message", "Ghế đã có người đặt trước",
                    "conflictSeats", conflictSeatNames
            ));
        }

        // 2. Giải phóng các ghế cũ đã giữ trước đó của user này
        seatRealtimeManager.releaseAllUserSeats(request.getScheduleId(), userId);

        // 3. Nếu tất cả đều rảnh -> Khóa các ghế mới cho user này
        for (Integer seatId : request.getSeatIds()) {
            seatRealtimeManager.holdSeat(request.getScheduleId(), seatId, userId);
        }

        // 3. Broadcast WebSocket cho toàn bộ phòng chiếu
        SeatRealtimeMessage msg = new SeatRealtimeMessage();
        msg.setScheduleId(request.getScheduleId());
        msg.setUserId(userId);
        msg.setType("HOLD_CHECKOUT");
        msg.setIsOccupied(2); // 2: Đã khóa (Đang giữ chỗ)
        msg.setHoldingSeatIds(seatRealtimeManager.getHoldingSeatIds(request.getScheduleId()));
        messagingTemplate.convertAndSend("/topic/seats/" + request.getScheduleId(), msg);

        log.info("User {} held checkout for seats {} in schedule {}", userId, request.getSeatIds(), request.getScheduleId());
        return ResponseEntity.ok(Map.of("success", true, "message", "Khóa ghế thành công"));
    }

    @PostMapping("/release-seats")
    public ResponseEntity<?> releaseSeats(@RequestBody HoldSeatsRequest request) {
        if (request.getScheduleId() == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
        Integer userId = request.getUserId() != null ? request.getUserId() : 1;
        if (request.getSeatIds() != null && !request.getSeatIds().isEmpty()) {
            for (Integer seatId : request.getSeatIds()) {
                seatRealtimeManager.releaseSeat(request.getScheduleId(), seatId, userId);
            }
        } else {
            seatRealtimeManager.releaseAllUserSeats(request.getScheduleId(), userId);
        }

        SeatRealtimeMessage msg = new SeatRealtimeMessage();
        msg.setScheduleId(request.getScheduleId());
        msg.setUserId(userId);
        msg.setType("RELEASE_CHECKOUT");
        msg.setIsOccupied(0);
        msg.setHoldingSeatIds(seatRealtimeManager.getHoldingSeatIds(request.getScheduleId()));
        messagingTemplate.convertAndSend("/topic/seats/" + request.getScheduleId(), msg);

        return ResponseEntity.ok(Map.of("success", true));
    }
}