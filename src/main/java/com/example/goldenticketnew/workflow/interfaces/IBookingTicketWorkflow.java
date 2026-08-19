package com.example.goldenticketnew.workflow.interfaces;

import com.example.goldenticketnew.config.cadance.CadenceWorkflowConfig;
import com.example.goldenticketnew.dtos.BookingRequestDto;
import com.uber.cadence.workflow.WorkflowMethod;



public interface IBookingTicketWorkflow {
    /** @return greeting string */
    @WorkflowMethod()
    void getBooking(BookingRequestDto request , CadenceWorkflowConfig config);
}