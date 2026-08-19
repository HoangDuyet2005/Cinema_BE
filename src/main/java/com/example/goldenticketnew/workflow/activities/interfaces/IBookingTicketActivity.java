package com.example.goldenticketnew.workflow.activities.interfaces;

import com.example.goldenticketnew.dtos.BookingRequestDto;
import com.uber.cadence.activity.ActivityMethod;

public interface IBookingTicketActivity {
    @ActivityMethod()
    void composeBooking(BookingRequestDto request);
}