package com.example.goldenticketnew.service.pricing;

import com.example.goldenticketnew.enums.SeatType;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.model.Seat;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Service
public class PriceCalculationService {

    /**
     * Tính toán giá vé thực tế cho 1 ghế trong 1 suất chiếu cụ thể:
     * Giá vé = Giá gốc + Phụ thu định dạng (2D/3D/IMAX) + Phụ thu loại ghế (Thường/VIP/Đôi) + Phụ thu ngày (Thường/Cuối tuần) + Phụ thu khung giờ (Sáng/Tối)
     */
    public double calculateSeatPrice(Schedule schedule, Seat seat) {
        double basePrice = schedule != null && schedule.getPrice() > 0 ? schedule.getPrice() : 80000.0;

        // 1. Phụ thu định dạng phòng/suất chiếu (2D, 3D, IMAX, 4DX, ScreenX)
        double formatSurcharge = getFormatSurcharge(schedule);

        // 2. Phụ thu loại ghế (Ghế thường, VIP, Ghế đôi Couple)
        double seatSurcharge = getSeatSurcharge(seat != null ? seat.getSeatType() : SeatType.NORMAL);

        // 3. Phụ thu ngày trong tuần (Thứ 2-5 vs Cuối tuần T6-CN)
        double daySurcharge = getDaySurcharge(schedule);

        // 4. Phụ thu khung giờ chiếu (Suất sáng sớm, Giờ vàng tối)
        double timeSurcharge = getTimeSurcharge(schedule);

        double finalPrice = basePrice + formatSurcharge + seatSurcharge + daySurcharge + timeSurcharge;
        return Math.max(50000.0, finalPrice);
    }

    public double getFormatSurcharge(Schedule schedule) {
        if (schedule == null) return 0.0;
        String format = schedule.getFormat();
        if (format == null && schedule.getRoom() != null) {
            format = schedule.getRoom().getFormat();
        }
        if (format == null) return 0.0;

        switch (format.trim().toUpperCase()) {
            case "3D": return 20000.0;
            case "IMAX": return 50000.0;
            case "4DX": return 60000.0;
            case "SCREENX": return 30000.0;
            case "GOLD CLASS": return 70000.0;
            default: return 0.0; // 2D
        }
    }

    public double getSeatSurcharge(SeatType seatType) {
        if (seatType == null) return 0.0;
        switch (seatType) {
            case VIP: return 15000.0;
            case COUPLE: return 40000.0;
            case TRIPLE: return 60000.0;
            case NORMAL:
            default: return 0.0;
        }
    }

    public double getDaySurcharge(Schedule schedule) {
        if (schedule == null || schedule.getStartDate() == null) return 0.0;
        DayOfWeek day = schedule.getStartDate().getDayOfWeek();
        if (day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return 15000.0; // Phụ thu cuối tuần
        }
        return 0.0; // Ngày thường
    }

    public double getTimeSurcharge(Schedule schedule) {
        if (schedule == null || schedule.getStartTime() == null) return 0.0;
        LocalTime time = schedule.getStartTime();
        if (time.isBefore(LocalTime.of(12, 0))) {
            return -10000.0; // Giảm giá suất chiếu sáng sớm
        } else if (time.isAfter(LocalTime.of(17, 0)) && time.isBefore(LocalTime.of(22, 0))) {
            return 10000.0; // Phụ thu giờ vàng buổi tối
        } else if (time.isAfter(LocalTime.of(22, 0))) {
            return -10000.0; // Giảm giá suất chiếu đêm khuya
        }
        return 0.0; // Suất chiều chuẩn
    }
}
