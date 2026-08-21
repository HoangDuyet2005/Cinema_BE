package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.SeatRealtimeMessage;
import com.example.goldenticketnew.service.seat.SeatRealtimeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketDisconnectListener {

    private final SeatRealtimeManager realtimeManager;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        SeatRealtimeManager.SessionDisconnectResult result = realtimeManager.handleSessionDisconnect(sessionId);
        if (result != null && result.getScheduleId() != null) {
            SeatRealtimeMessage msg = new SeatRealtimeMessage();
            msg.setScheduleId(result.getScheduleId());
            msg.setUserId(result.getUserId());
            msg.setType("SYNC");
            msg.setHoldingSeatIds(realtimeManager.getHoldingSeatIds(result.getScheduleId()));
            messagingTemplate.convertAndSend("/topic/seats/" + result.getScheduleId(), msg);
            log.info("Client disconnected (sessionId: {}). Released seats {} for schedule {}", sessionId, result.getReleasedSeatIds(), result.getScheduleId());
        }
    }
}