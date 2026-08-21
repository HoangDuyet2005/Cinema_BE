package com.example.goldenticketnew.service.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SeatRealtimeManager {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockInfo {
        private Integer userId;
        private String sessionId;
        private LocalDateTime expireTime;

        public LockInfo(Integer userId, String sessionId, int expireMinutes) {
            this.userId = userId;
            this.sessionId = sessionId;
            this.expireTime = LocalDateTime.now().plusMinutes(expireMinutes);
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expireTime);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SessionDisconnectResult {
        private Integer scheduleId;
        private Integer userId;
        private Set<Integer> releasedSeatIds;
    }

    // scheduleId -> (seatId -> LockInfo)
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, LockInfo>> scheduleLocks = new ConcurrentHashMap<>();

    // sessionId -> (scheduleId, userId)
    private final ConcurrentHashMap<String, Map.Entry<Integer, Integer>> sessionUserMap = new ConcurrentHashMap<>();

    // userId -> scheduleId
    private final ConcurrentHashMap<Integer, Integer> userScheduleMap = new ConcurrentHashMap<>();

    public synchronized void registerSession(String sessionId, Integer scheduleId, Integer userId) {
        if (sessionId != null && scheduleId != null && userId != null) {
            sessionUserMap.put(sessionId, new AbstractMap.SimpleEntry<>(scheduleId, userId));
            userScheduleMap.put(userId, scheduleId);
        }
    }

    public synchronized boolean holdSeat(Integer scheduleId, Integer seatId, Integer userId, String sessionId) {
        if (scheduleId == null || seatId == null || userId == null) return false;

        scheduleLocks.putIfAbsent(scheduleId, new ConcurrentHashMap<>());
        ConcurrentHashMap<Integer, LockInfo> locks = scheduleLocks.get(scheduleId);

        LockInfo current = locks.get(seatId);
        if (current != null && !current.isExpired() && !current.getUserId().equals(userId)) {
            // Ghế đang bị user khác giữ và chưa hết hạn
            return false;
        }

        // Khóa ghế cho user này trong 5 phút
        locks.put(seatId, new LockInfo(userId, sessionId, 5));
        userScheduleMap.put(userId, scheduleId);
        if (sessionId != null) {
            sessionUserMap.put(sessionId, new AbstractMap.SimpleEntry<>(scheduleId, userId));
        }
        return true;
    }

    public synchronized boolean holdSeat(Integer scheduleId, Integer seatId, Integer userId) {
        return holdSeat(scheduleId, seatId, userId, null);
    }

    public synchronized void releaseSeat(Integer scheduleId, Integer seatId, Integer userId) {
        if (scheduleId == null || seatId == null) return;
        ConcurrentHashMap<Integer, LockInfo> locks = scheduleLocks.get(scheduleId);
        if (locks != null) {
            LockInfo info = locks.get(seatId);
            if (info != null && (userId == null || info.getUserId().equals(userId))) {
                locks.remove(seatId);
            }
        }
    }

    public synchronized void releaseAllUserSeats(Integer scheduleId, Integer userId) {
        if (scheduleId == null || userId == null) return;
        ConcurrentHashMap<Integer, LockInfo> locks = scheduleLocks.get(scheduleId);
        if (locks != null) {
            locks.entrySet().removeIf(entry -> entry.getValue().getUserId().equals(userId) || entry.getValue().isExpired());
        }
    }

    public synchronized SessionDisconnectResult handleSessionDisconnect(String sessionId) {
        if (sessionId == null) return null;
        Map.Entry<Integer, Integer> mapping = sessionUserMap.remove(sessionId);
        if (mapping == null) return null;

        Integer scheduleId = mapping.getKey();
        Integer userId = mapping.getValue();
        Set<Integer> releasedSeatIds = new HashSet<>();

        ConcurrentHashMap<Integer, LockInfo> locks = scheduleLocks.get(scheduleId);
        if (locks != null) {
            locks.entrySet().removeIf(entry -> {
                LockInfo info = entry.getValue();
                boolean shouldRemove = (info.getSessionId() != null && info.getSessionId().equals(sessionId))
                        || (info.getUserId() != null && info.getUserId().equals(userId))
                        || info.isExpired();
                if (shouldRemove) {
                    releasedSeatIds.add(entry.getKey());
                }
                return shouldRemove;
            });
        }

        return new SessionDisconnectResult(scheduleId, userId, releasedSeatIds);
    }

    public List<Integer> getHoldingSeatIds(Integer scheduleId) {
        if (scheduleId == null) return Collections.emptyList();
        ConcurrentHashMap<Integer, LockInfo> locks = scheduleLocks.get(scheduleId);
        if (locks == null) return Collections.emptyList();

        // Dọn dẹp các lock đã hết hạn
        locks.entrySet().removeIf(entry -> entry.getValue().isExpired());

        return new ArrayList<>(locks.keySet());
    }

    public Integer getHoldingUserId(Integer scheduleId, Integer seatId) {
        if (scheduleId == null || seatId == null) return null;
        ConcurrentHashMap<Integer, LockInfo> locks = scheduleLocks.get(scheduleId);
        if (locks == null) return null;
        LockInfo info = locks.get(seatId);
        if (info != null && !info.isExpired()) {
            return info.getUserId();
        }
        return null;
    }
}