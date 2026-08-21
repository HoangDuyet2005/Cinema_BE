package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.SeatRealtimeMessage;
import com.example.goldenticketnew.service.seat.SeatRealtimeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SeatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SeatRealtimeManager realtimeManager;

    @MessageMapping("/seats/register")
    public void registerSession(@Payload SeatRealtimeMessage message, StompHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        if (message.getScheduleId() != null && message.getUserId() != null) {
            realtimeManager.registerSession(sessionId, message.getScheduleId(), message.getUserId());
            log.info("Registered WebSocket session {} for user {} in schedule {}", sessionId, message.getUserId(), message.getScheduleId());
        }
    }

    @MessageMapping("/seats/select")
    public void selectSeat(@Payload SeatRealtimeMessage message, StompHeaderAccessor headerAccessor) {
        if (message.getScheduleId() == null || message.getSeatId() == null) return;
        String sessionId = headerAccessor.getSessionId();

        boolean success = realtimeManager.holdSeat(message.getScheduleId(), message.getSeatId(), message.getUserId(), sessionId);
        if (success) {
            message.setType("HOLD");
            message.setIsOccupied(2); // 2: Đang giữ chỗ
            message.setHoldingSeatIds(realtimeManager.getHoldingSeatIds(message.getScheduleId()));
            log.info("User {} is HOLDING seat {} for schedule {}", message.getUserId(), message.getSeatId(), message.getScheduleId());
            messagingTemplate.convertAndSend("/topic/seats/" + message.getScheduleId(), message);
        }
    }

    @MessageMapping("/seats/unselect")
    public void unselectSeat(@Payload SeatRealtimeMessage message) {
        if (message.getScheduleId() == null || message.getSeatId() == null) return;

        realtimeManager.releaseSeat(message.getScheduleId(), message.getSeatId(), message.getUserId());
        message.setType("RELEASE");
        message.setIsOccupied(0); // 0: Trống
        message.setHoldingSeatIds(realtimeManager.getHoldingSeatIds(message.getScheduleId()));
        log.info("User {} RELEASED seat {} for schedule {}", message.getUserId(), message.getSeatId(), message.getScheduleId());
        messagingTemplate.convertAndSend("/topic/seats/" + message.getScheduleId(), message);
    }

    @MessageMapping("/seats/release-all")
    public void releaseAllSeats(@Payload SeatRealtimeMessage message) {
        if (message.getScheduleId() == null || message.getUserId() == null) return;

        realtimeManager.releaseAllUserSeats(message.getScheduleId(), message.getUserId());
        message.setType("SYNC");
        message.setHoldingSeatIds(realtimeManager.getHoldingSeatIds(message.getScheduleId()));
        messagingTemplate.convertAndSend("/topic/seats/" + message.getScheduleId(), message);
    }
}